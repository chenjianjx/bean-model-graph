package org.beanmodelgraph.drawer.model;

import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class DotGraph {
    private List<DotNode> nodes;
    private List<DotEdge> edges;

    public DotGraph() {
        nodes = new ArrayList<>();
        edges = new ArrayList<>();
    }

    public void addNode(DotNode node) {
        nodes.add(node);
    }

    public void addEdge(DotEdge edge) {
        edges.add(edge);
    }
}