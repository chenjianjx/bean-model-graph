package org.beanmodelgraph.constructor.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
public abstract class BmgEdge {

    public abstract BmgEdgeColor getColor();

    @Getter
    @NonNull
    private BmgNode endingNode;

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
