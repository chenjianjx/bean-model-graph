package org.beanmodelgraph.constructor.model;


import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.SuperBuilder;

@Value
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class BmgHasAEdge extends BmgEdge {

    @NonNull
    private String propName;
    private boolean collectionProp;


    @Override
    public BmgEdgeColor getColor() {
        return BmgEdgeColor.HAS_A;
    }
}
