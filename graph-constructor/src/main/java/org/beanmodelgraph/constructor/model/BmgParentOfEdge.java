package org.beanmodelgraph.constructor.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@AllArgsConstructor
@ToString
public class BmgParentOfEdge extends BmgEdge {
    @Override
    public BmgEdgeColor getColor() {
        return BmgEdgeColor.PARENT_OF;
    }
}
