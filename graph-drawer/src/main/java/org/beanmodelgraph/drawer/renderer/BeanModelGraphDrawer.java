package org.beanmodelgraph.drawer.renderer;

import org.beanmodelgraph.drawer.model.DotEdge;
import org.beanmodelgraph.drawer.model.DotGraph;
import org.beanmodelgraph.drawer.model.DotNode;

public class BeanModelGraphDrawer {

    public String render(DotGraph dotGraph) {
        StringBuilder dotScript = new StringBuilder();
        dotScript.append("digraph {\n");
        dotScript.append("node [shape=rectangle, style=rounded];\n");

        // Append nodes
        for (DotNode node : dotGraph.getNodes()) {
            dotScript.append("[").append(node.getLabel())
                    .append("];\n");
        }

        // Append edges
        for (DotEdge edge : dotGraph.getEdges()) {
            dotScript.append("[").append(edge.getSource()).append(" -> ")
                    .append(edge.getTarget())
                    .append(" [label=\"").append(edge.getLabel())
                    .append("\", arrowhead=\"").append(edge.getArrowhead())
                    .append(", style=\"").append(edge.getStyle()).append("\"];\n");
        }

        dotScript.append("}\n");
        return dotScript.toString();
    }
}