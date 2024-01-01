package org.beanmodelgraph.builder.model;

import lombok.NonNull;

import java.util.Optional;

public abstract class BmgEdge {

    /**
     * A.k.a. `color` in a multigragh (graph theory)
     */
    public abstract BmgEdgeType getEdgeType();

    @NonNull
    private BmgNode startingNode;

    @NonNull
    private Optional<BmgNode> endingNode;


}
