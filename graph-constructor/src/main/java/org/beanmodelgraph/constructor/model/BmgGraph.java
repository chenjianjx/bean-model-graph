package org.beanmodelgraph.constructor.model;


import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class BmgGraph {

    @NonNull
    private BmgNode rootNode;
}
