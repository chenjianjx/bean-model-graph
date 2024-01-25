package org.beanmodelgraph.drawer.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DotEdge {
    private String source;
    private String target;
    private String label;
    private String arrowhead;
    private String style;
}
