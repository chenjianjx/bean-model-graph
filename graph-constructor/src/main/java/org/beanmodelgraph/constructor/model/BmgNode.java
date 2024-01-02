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

    public BmgNode(@NonNull Class<?> beanClass) {
        this.beanClass = beanClass;
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

    public void removeEdge(BmgEdge edge) {
        edges.remove(edge);
    }
}
