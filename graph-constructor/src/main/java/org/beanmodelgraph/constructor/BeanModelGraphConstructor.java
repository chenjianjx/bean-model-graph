package org.beanmodelgraph.constructor;


import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtils;
import org.beanmodelgraph.constructor.helper.AtomicTypeResolver;
import org.beanmodelgraph.constructor.helper.InheritanceService;
import org.beanmodelgraph.constructor.model.BmgEdge;
import org.beanmodelgraph.constructor.model.BmgHasAEdge;
import org.beanmodelgraph.constructor.model.BmgNode;
import org.beanmodelgraph.constructor.model.BmgParentOfEdge;
import org.beanmodelgraph.constructor.util.GenericsUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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
    private AtomicTypeResolver atomicTypeResolver = new AtomicTypeResolver();

    private InheritanceService inheritanceService = new InheritanceService();

    private final Class<?> rootBeanClass;

    private List<String> subTypeScanBasePackages;

    public BeanModelGraphConstructor(@NonNull Class<?> rootBeanClass, @NonNull List<String> subTypeScanBasePackages) {
        this.rootBeanClass = rootBeanClass;
        this.subTypeScanBasePackages = subTypeScanBasePackages;
    }

    /**
     * construct a graph.
     *
     * @return The root node of this graph
     */
    public BmgNode construct() {
        return doConstruct(rootBeanClass);
    }

    private BmgNode doConstruct(Class<?> beanClass) {
        Optional<BmgNode> expandedNodeOpt = Optional.ofNullable(expandedNodes.get(beanClass));
        if (expandedNodeOpt.isPresent()) {
            return expandedNodeOpt.get();
        }


        BmgNode rootNode = new BmgNode(beanClass);
        expandedNodes.put(beanClass, rootNode);

        if (atomicTypeResolver.isAtomicType(beanClass)) {
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
                .map(pd -> toOutgoingHasAEdge(pd)).filter(eo -> eo.isPresent())
                .map(eo -> eo.get()).collect(Collectors.toList());
        return hasAEdges;
    }

    private Optional<BmgHasAEdge> toOutgoingHasAEdge(PropertyDescriptor pd) {

        Optional<Method> getterOpt = Optional.ofNullable(pd.getReadMethod());
        if (!getterOpt.isPresent()) {
            log.warn("Cannot find getter method for property {}. Will skip this property", pd.getName());
            return Optional.empty();
        }

        BmgHasAEdge.BmgHasAEdgeBuilder edgeBuilder = BmgHasAEdge.builder();
        edgeBuilder.propName(pd.getName());
        edgeBuilder.collectionProp(isClassArrayOrCollection(getterOpt.get().getReturnType()));
        edgeBuilder.endingNode(doConstruct(GenericsUtils.getMethodGenericReturnType(getterOpt.get())));

        return Optional.of(edgeBuilder.build());
    }

    private boolean isClassArrayOrCollection(Class<?> clazz) {
        return clazz.isArray() || Collection.class.isAssignableFrom(clazz);
    }
}
