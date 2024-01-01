package org.beanmodelgraph.builder.model;

import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
public class BmgParentOfEdge extends BmgEdge{
    @Override
    public BmgEdgeType getEdgeType() {
        return BmgEdgeType.PARENT_OF;
    }
}
