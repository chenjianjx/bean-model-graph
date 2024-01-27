package org.beanmodelgraph.constructor;


import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtils;
import org.beanmodelgraph.constructor.helper.AtomicTypeResolver;
import org.beanmodelgraph.constructor.helper.InheritanceService;
import org.beanmodelgraph.constructor.model.BmgEdge;
import org.beanmodelgraph.constructor.model.BmgEdgeColor;
import org.beanmodelgraph.constructor.model.BmgHasAEdge;
import org.beanmodelgraph.constructor.model.BmgNode;
import org.beanmodelgraph.constructor.model.BmgParentOfEdge;
import org.beanmodelgraph.constructor.traverse.BmgDfsTraverser;
import org.beanmodelgraph.constructor.traverse.BmgNodeDfsListener;
import org.beanmodelgraph.constructor.util.CollectionTypeUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * The graph will be "Colored Multigraph" in graph theory
 * * Nodes are java types
 * * Edges can be one of 2 "colors":
 * * A "has a" B property
 * * A "is a parent" of B class
 * <p>
 * <p>
 * <p>
 * Thread-safety:  not safe, one-time use
 */
@Slf4j
public class BeanModelGraphConstructor {

    /**
     * a node here is not only constructed, but construction of its edges are in progress
     */
    Map<Class<?>, BmgNode> expandedNodes = new HashMap<>();
    private AtomicTypeResolver atomicTypeResolver;

    private InheritanceService inheritanceService = new InheritanceService();

    private final Class<?> rootBeanClass;

    private List<String> subTypeScanBasePackages;

    public BeanModelGraphConstructor(@NonNull Class<?> rootBeanClass,
                                     @NonNull List<String> subTypeScanBasePackages,
                                     @NonNull Set<Class<?>> additionalAtomicTypes
    ) {
        this.rootBeanClass = rootBeanClass;
        this.subTypeScanBasePackages = subTypeScanBasePackages;
        this.atomicTypeResolver = new AtomicTypeResolver(additionalAtomicTypes);
    }

    /**
     * construct a graph.
     *
     * @return The root node of this graph
     */
    public BmgNode construct() {
        BmgNode rootNode = doConstruct(rootBeanClass);
        removeUnnecessaryHasAEdges(rootNode);
        return rootNode;
    }


    private BmgNode doConstruct(Class<?> beanClass) {
        Optional<BmgNode> expandedNodeOpt = Optional.ofNullable(expandedNodes.get(beanClass));
        if (expandedNodeOpt.isPresent()) {
            return expandedNodeOpt.get();
        }


        BmgNode rootNode = new BmgNode(beanClass);
        expandedNodes.put(beanClass, rootNode);

        if (atomicTypeResolver.isAtomicType(beanClass)
                || CollectionTypeUtils.isClassArrayOrCollection(beanClass)
                || CollectionTypeUtils.isMap(beanClass)
        ) {
            rootNode.setEdges(Collections.emptyList());
        } else {
            List<BmgHasAEdge> hasAEdges = getHasAEdges(beanClass);
            List<BmgParentOfEdge> parentOfEdges = getParentOfEdges(beanClass);
            List<BmgEdge> allEdges = new ArrayList<>();
            allEdges.addAll(hasAEdges);
            allEdges.addAll(parentOfEdges);
            rootNode.setEdges(allEdges);
        }
        return rootNode;
    }

    private <T> List<BmgParentOfEdge> getParentOfEdges(Class<T> beanClass) {
        Set<Class<? extends T>> directSubTypes = inheritanceService.getDirectSubTypes(beanClass, subTypeScanBasePackages);
        return directSubTypes.stream().map(subType -> BmgParentOfEdge.builder()
                        .endingNode(doConstruct(subType))
                        .build())
                .collect(Collectors.toList());
    }

    private List<BmgHasAEdge> getHasAEdges(Class<?> beanClass) {
        List<PropertyDescriptor> propertyDescriptors =
                Arrays.stream(PropertyUtils.getPropertyDescriptors(beanClass))
                        .sorted(
                                Comparator
                                        //show atomic types first
                                        .comparing((Function<PropertyDescriptor, Boolean>) pd ->
                                                !atomicTypeResolver.isAtomicType(pd.getPropertyType()))
                                        .thenComparing(PropertyDescriptor::getName)
                        )
                        .collect(Collectors.toList());
        List<BmgHasAEdge> hasAEdges = propertyDescriptors.stream()
                .map(pd -> toOutgoingHasAEdge(beanClass, pd)).filter(eo -> eo.isPresent())
                .map(eo -> eo.get()).collect(Collectors.toList());
        return hasAEdges;
    }

    private Optional<BmgHasAEdge> toOutgoingHasAEdge(Class<?> beanClass, PropertyDescriptor pd) {

        Optional<Method> getterOpt = Optional.ofNullable(pd.getReadMethod());
        if (!getterOpt.isPresent()) {
            log.warn("Cannot find getter method for property {}.{}. Will skip this property", beanClass.getSimpleName(), pd.getName());
            return Optional.empty();
        }else {
            log.info("Build {} edge for property {}.{}", BmgEdgeColor.HAS_A, beanClass.getSimpleName(), pd.getName());
        }


        Method getter = getterOpt.get();
        BmgHasAEdge.BmgHasAEdgeBuilder edgeBuilder = BmgHasAEdge.builder();
        edgeBuilder.propName(pd.getName());

        Class<?> getterReturnType = getter.getReturnType();
        boolean multiOccur = CollectionTypeUtils.isClassArrayOrCollection(getterReturnType);
        edgeBuilder.multiOccur(multiOccur);

        Class<?> endingNodeBeanClass = multiOccur ?
                CollectionTypeUtils.getMethodGenericReturnTypeIfArrayOrCollection(getter) : getterReturnType;
        edgeBuilder.endingNode(doConstruct(endingNodeBeanClass));

        return Optional.of(edgeBuilder.build());
    }

    private void removeUnnecessaryHasAEdges(BmgNode rootNode) {

        BmgDfsTraverser traverser = new BmgDfsTraverser(
                new HasAEdgeByInheritanceRemover(expandedNodes.keySet(), this.inheritanceService)
        );

        traverser.traverse(rootNode);
    }


    private static final class HasAEdgeByInheritanceRemover implements BmgNodeDfsListener {

        private Set<Class<?>> allClassesInGraph;
        private InheritanceService inheritanceService;

        public HasAEdgeByInheritanceRemover(Set<Class<?>> allClassesInGraph, InheritanceService inheritanceService) {
            this.allClassesInGraph = allClassesInGraph;
            this.inheritanceService = inheritanceService;
        }

        @Override
        public void onNode(List<BmgEdge> pathOfThisNode, BmgNode node, Optional<BmgNode> prevNodeOpt) {
            if (!prevNodeOpt.isPresent()) {
                return;
            }

            BmgEdge edgeToThisNode = pathOfThisNode.get(pathOfThisNode.size() - 1);
            if (!(edgeToThisNode instanceof BmgHasAEdge)) {
                return;
            }


            BmgHasAEdge edge = (BmgHasAEdge) edgeToThisNode;
            BmgNode prevNode = prevNodeOpt.get();
            Class<?> prevNodeBeanClass = prevNode.getBeanClass();
            Method getter =
                    Arrays.stream(PropertyUtils.getPropertyDescriptors(prevNodeBeanClass))
                            .filter(pd -> pd.getName().equals(edge.getPropName()))
                            .findFirst().get().getReadMethod();
            if (inheritanceService.isMethodInheritedFrom(prevNodeBeanClass, getter, allClassesInGraph)) {
                log.info("Remove edge {}.{} because the property is inherited", prevNodeBeanClass.getSimpleName(), edge.getPropName());
                prevNode.removeEdge(edge);
            }

        }

    }
}
