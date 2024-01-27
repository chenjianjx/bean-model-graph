package org.beanmodelgraph.constructor.model;


import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import java.util.List;


@Getter
@ToString
public class BmgNode {

    private final Class<?> beanClass;

    @NonNull
    @Setter
    private List<BmgEdge> edges;

    private boolean atomicType;

    public BmgNode(@NonNull Class<?> beanClass, boolean atomicType) {
        this.beanClass = beanClass;
        this.atomicType = atomicType;
    }

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

    public void removeEdge(BmgEdge edge) {
        edges.remove(edge);
    }
}
