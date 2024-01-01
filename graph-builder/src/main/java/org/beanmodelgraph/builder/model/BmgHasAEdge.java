package org.beanmodelgraph.builder.model;


import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
public class BmgHasAEdge extends BmgEdge {

    private boolean collectionProp;


    @Override
    public BmgEdgeType getEdgeType() {
        return BmgEdgeType.HAS_A;
    }
}
