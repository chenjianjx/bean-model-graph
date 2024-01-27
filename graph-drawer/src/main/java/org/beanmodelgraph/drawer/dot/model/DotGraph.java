package org.beanmodelgraph.drawer.dot.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
@EqualsAndHashCode
public class DotGraph {
    private List<DotNode> nodes = new ArrayList<>();
    private List<DotEdge> edges = new ArrayList<>();
}