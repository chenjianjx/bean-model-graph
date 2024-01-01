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
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * The graph will be "Colored Multigraph" in graph theory
 * * Nodes are java types
 * * Edges can be one of 2 "colors":
 * * A "has a" B property
 * * A "is a parent" of B class
 */
@Slf4j
public class BeanModelGraphConstructor {

    /**
     * construct a graph.
     *
     * @param beanClass
     * @return The root node of this graph
     */
    public BmgNode constructFromBeanClass(Class<?> beanClass) {
        AtomicTypeResolver atomicTypeResolver = new AtomicTypeResolver();
        BmgNode.BmgNodeBuilder rootNodeBuilder = BmgNode.builder().type(beanClass);

        if (atomicTypeResolver.isAtomicType(beanClass)) {
            return rootNodeBuilder.edges(Collections.emptyList()).build();
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
                    .map(pd -> toEdge(pd)).filter(eo -> eo.isPresent())
                    .map(eo -> eo.get()).collect(Collectors.toList());
            rootNodeBuilder.edges(edges);
            return rootNodeBuilder.build();
        }


    }

    private Optional<BmgEdge> toEdge(PropertyDescriptor pd) {

        Method getter = pd.getReadMethod();
        if (getter == null) {
            log.warn("Cannot find getter method for property {}. Will skip this property", pd.getName());
            return Optional.empty();
        }

        BmgHasAEdge.BmgHasAEdgeBuilder edgeBuilder = BmgHasAEdge.builder();
        edgeBuilder.propName(pd.getName());
        edgeBuilder.endingNode(constructFromBeanClass(GenericsUtils.getMethodGenericReturnType(getter)));

        return Optional.of(edgeBuilder.build());
    }

    private boolean isClassArrayOrCollection(Class<?> clazz) {
        return clazz.isArray() || Collection.class.isAssignableFrom(clazz);
    }
}
