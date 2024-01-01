package org.beanmodelgraph.constructor.model;

import lombok.NonNull;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public abstract class BmgEdge {

    public abstract BmgEdgeColor getColor();

    @NonNull
    private BmgNode endingNode;


}
