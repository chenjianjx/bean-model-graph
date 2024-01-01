package org.beanmodelgraph.constructor.model;

import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
public class BmgParentOfEdge extends BmgEdge{
    @Override
    public BmgEdgeColor getColor() {
        return BmgEdgeColor.PARENT_OF;
    }
}
