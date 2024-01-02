package org.beanmodelgraph.constructor;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtils;
import org.beanmodelgraph.constructor.helper.AtomicTypeResolver;
import org.beanmodelgraph.constructor.model.BmgEdge;
import org.beanmodelgraph.constructor.model.BmgHasAEdge;
import org.beanmodelgraph.constructor.model.BmgNode;
import org.beanmodelgraph.constructor.util.GenericsUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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

    Map<Class<?>, BmgNode> existingNodes = new HashMap<>();
    private AtomicTypeResolver atomicTypeResolver = new AtomicTypeResolver();

    private final Class<?> rootBeanClass;

    public BeanModelGraphConstructor(Class<?> rootBeanClass) {
        this.rootBeanClass = rootBeanClass;
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
        Optional<BmgNode> existingNodeOpt = Optional.ofNullable(existingNodes.get(beanClass));
        if (existingNodeOpt.isPresent()) {
            return existingNodeOpt.get();
        }


        BmgNode rootNode = new BmgNode(beanClass);
        existingNodes.put(beanClass, rootNode);

        if (atomicTypeResolver.isAtomicType(beanClass)) {
            rootNode.setEdges(Collections.emptyList());
        } else {
            List<PropertyDescriptor> propertyDescriptors =
                    Arrays.stream(PropertyUtils.getPropertyDescriptors(beanClass))
                            .sorted(
                                    Comparator
                                            //show atomic types first
                                            .comparing((Function<PropertyDescriptor, Boolean>) pd1 ->
                                                    !atomicTypeResolver.isAtomicType(pd1.getPropertyType()))
                                            .thenComparing(PropertyDescriptor::getName)
                            )
                            .collect(Collectors.toList());
            List<BmgEdge> edges = propertyDescriptors.stream()
                    .map(pd -> toOutgoingEdge(pd)).filter(eo -> eo.isPresent())
                    .map(eo -> eo.get()).collect(Collectors.toList());
            rootNode.setEdges(edges);
        }
        return rootNode;
    }

    private Optional<BmgEdge> toOutgoingEdge(PropertyDescriptor pd) {

        Method getter = pd.getReadMethod();
        if (getter == null) {
            log.warn("Cannot find getter method for property {}. Will skip this property", pd.getName());
            return Optional.empty();
        }

        BmgHasAEdge.BmgHasAEdgeBuilder edgeBuilder = BmgHasAEdge.builder();
        edgeBuilder.propName(pd.getName());
        edgeBuilder.endingNode(doConstruct(GenericsUtils.getMethodGenericReturnType(getter)));

        return Optional.of(edgeBuilder.build());
    }

    private boolean isClassArrayOrCollection(Class<?> clazz) {
        return clazz.isArray() || Collection.class.isAssignableFrom(clazz);
    }
}
