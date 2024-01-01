package org.beanmodelgraph.constructor.model;


import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.List;


@Builder
@Value
public class BmgNode {

    @NonNull
    private Class<?> type;

    @NonNull
    private List<BmgEdge> edges;
}
