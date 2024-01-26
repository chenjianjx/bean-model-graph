package org.beanmodelgraph.drawer.renderer;

import org.beanmodelgraph.drawer.model.DotEdge;
import org.beanmodelgraph.drawer.model.DotGraph;
import org.beanmodelgraph.drawer.model.DotNode;

public class DefaultDotGraphRenderer implements DotGraphRenderer {

    @Override
    public String render(DotGraph dotGraph) { //TODO: make this code more structural
        StringBuilder dotScript = new StringBuilder();
        dotScript.append("digraph {\n\n");
        dotScript.append("    node [shape=rectangle, style=rounded];\n");
        dotScript.append("    rankdir=\"LR\";\n\n");


        // Append nodes
        for (DotNode node : dotGraph.getNodes()) {
            dotScript.append("    ").append(node.getName())
                    .append(" [label=").append(doubleQuote(node.getLabel())).append("];\n");
        }

        dotScript.append("\n\n\n");

        // Append edges
        for (DotEdge edge : dotGraph.getEdges()) {
            dotScript.append("    ").append(edge.getSource()).append(" -> ")
                    .append(edge.getTarget());
            dotScript.append(" [");
            dotScript.append("label=").append(doubleQuote(edge.getLabel().orElse("")));
            dotScript.append(", arrowhead=").append(doubleQuote(edge.getArrowhead()));
            dotScript.append(", style=").append(doubleQuote(edge.getStyle()));
            dotScript.append("];\n");
        }

        dotScript.append("\n}");
        return dotScript.toString();
    }

    private String doubleQuote(String s) { //TODO: move this to common util
        return "\"" + s + "\"";
    }
}