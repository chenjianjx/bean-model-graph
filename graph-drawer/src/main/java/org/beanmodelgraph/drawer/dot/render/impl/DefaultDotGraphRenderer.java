package org.beanmodelgraph.drawer.dot.render.impl;

import lombok.NonNull;
import org.beanmodelgraph.drawer.dot.model.DotEdge;
import org.beanmodelgraph.drawer.dot.model.DotGraph;
import org.beanmodelgraph.drawer.dot.model.DotNode;
import org.beanmodelgraph.drawer.dot.render.DotGraphRenderer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.beanmodelgraph.constructor.util.BmgStringUtils.doubleQuote;
import static org.beanmodelgraph.constructor.util.BmgStringUtils.join;

/**
 * stateless
 */
public class DefaultDotGraphRenderer implements DotGraphRenderer {
    private static final String INDENT = "    ";

    @Override
    public String render(@NonNull DotGraph dotGraph, @NonNull Optional<RenderOptions> renderOptions) {
        StringBuilder sb = new StringBuilder();
        appendNewLine(sb, "digraph {");
        appendNewLine(sb);
        appendNewLine(sb, INDENT + "node [shape=rectangle, style=rounded];");
        appendNewLine(sb);
        for (DotNode node : dotGraph.getNodes()) {
            appendNewLine(sb, renderNode(node));
        }
        appendNewLine(sb);
        appendNewLine(sb);
        for (DotEdge edge : dotGraph.getEdges()) {
            appendNewLine(sb, renderEdge(edge));
        }
        appendNewLine(sb);
        sb.append("}");
        return sb.toString();
    }

    private String renderEdge(DotEdge edge) {
        StringBuilder line = new StringBuilder();
        line.append(INDENT).append(edge.getSource().getName()).append(" -> ")
                .append(edge.getTarget().getName());
        line.append(" [");
        List<String> attrPairs = new ArrayList<>();
        edge.getLabel().ifPresent(label -> attrPairs.add("label=" + doubleQuote(label)));
        attrPairs.add("arrowhead=" + doubleQuote(edge.getArrowhead()));
        attrPairs.add("style=" + doubleQuote(edge.getStyle()));
        line.append(join(attrPairs, " ,"));
        line.append("];");
        return line.toString();
    }




    private String renderNode(DotNode node) {
        StringBuilder line = new StringBuilder();
        line.append(INDENT).append(node.getName());
        if (node.getLabel().isPresent()) {
            line.append(" [label=").append(doubleQuote(node.getLabel().get())).append("]");
        }
        return line.toString();
    }

    private static void appendNewLine(StringBuilder sb, String line) {
        sb.append(line).append("\n");
    }

    private static void appendNewLine(StringBuilder sb) {
        sb.append("\n");
    }


}