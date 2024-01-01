package org.beanmodelgraph.constructor.model;

import lombok.NonNull;

import java.util.Optional;

public abstract class BmgEdge {

    /**
     * A.k.a. `color` in a multigragh (graph theory)
     */
    public abstract BmgEdgeColor getColor();

    @NonNull
    private BmgNode startingNode;

    @NonNull
    private Optional<BmgNode> endingNode;


}
