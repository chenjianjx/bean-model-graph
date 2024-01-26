package org.beanmodelgraph.drawer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Optional;

@Data
@AllArgsConstructor
@Builder
public class DotEdge {
    private String source;
    private String target;
    private Optional<String> label;
    private String arrowhead;
    private String style;
}
