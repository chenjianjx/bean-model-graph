package org.beanmodelgraph.constructor;

import org.beanmodelgraph.constructor.model.BmgNode;

/**
 * The graph will be "Colored Multigraph" in graph theory
 * * Nodes are java types
 * * Edges can be one of 2 "colors":
 *   * A "has a" B property
 *   * A "is a parent" of B class
 */
public class BeanModelGraphConstructor {

    /**
     * build a graph.
     * @param beanClass
     * @return The root node of this graph
     */
    public BmgNode buildFromBeanClass(Class<?> beanClass) {
        BmgNode.BmgNodeBuilder rootNodeBuilder = BmgNode.builder().type(beanClass);
        return rootNodeBuilder.build();
    }
}
