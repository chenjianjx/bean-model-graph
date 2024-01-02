package org.beanmodelgraph.constructor.model;


import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import java.util.List;


@Getter
@ToString
public class BmgNode {


    private final Class<?> type;

    public BmgNode(@NonNull Class<?> type) {
        this.type = type;
    }

    @NonNull
    @Setter
    private List<BmgEdge> edges;

    /**
     * Only equals if the same object
     */
    @Override
    public boolean equals(Object obj) {
        return this == obj;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
