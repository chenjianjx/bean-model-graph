package org.beanmodelgraph.constructor.model;


import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
public class BmgHasAEdge extends BmgEdge {

    private boolean collectionProp;


    @Override
    public BmgEdgeColor getColor() {
        return BmgEdgeColor.HAS_A;
    }
}
