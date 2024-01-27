package org.beanmodelgraph.drawer.dot.model;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.Optional;


@Data
@Builder
public class DotEdge {

    @NonNull
    private DotNode source;

    @NonNull
    private DotNode target;

    @NonNull
    private Optional<String> label;

    @NonNull
    private String arrowhead;

    @NonNull
    private String style;

}
