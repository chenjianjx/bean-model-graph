package org.beanmodelgraph.constructor.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public abstract class BmgEdge {

    public abstract BmgEdgeColor getColor();

    @Getter
    @NonNull
    private BmgNode endingNode;


}
