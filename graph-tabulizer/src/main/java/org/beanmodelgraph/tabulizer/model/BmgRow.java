package org.beanmodelgraph.tabulizer.model;


import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.beanmodelgraph.constructor.model.BmgEdge;

import java.util.List;

@Value
@Builder
public class BmgRow {

    /**
     * The path from root to the row this property represents
     */
    @NonNull
    private List<BmgEdge> path;

    @NonNull
    private Class<?> type;

}