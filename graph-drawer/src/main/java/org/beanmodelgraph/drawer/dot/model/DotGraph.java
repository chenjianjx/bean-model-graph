package org.beanmodelgraph.drawer.dot.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class DotGraph {
    @NonNull
    private List<DotNode> nodes;

    @NonNull
    private List<DotEdge> edges;

}