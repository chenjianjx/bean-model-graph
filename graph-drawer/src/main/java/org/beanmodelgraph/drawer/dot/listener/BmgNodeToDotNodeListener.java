package org.beanmodelgraph.drawer.dot.listener;

import lombok.NonNull;
import org.beanmodelgraph.constructor.model.BmgEdge;
import org.beanmodelgraph.constructor.model.BmgHasAEdge;
import org.beanmodelgraph.constructor.model.BmgNode;
import org.beanmodelgraph.constructor.model.BmgParentOfEdge;
import org.beanmodelgraph.constructor.traverse.BmgNodeDfsListener;
import org.beanmodelgraph.drawer.dot.model.DotEdge;
import org.beanmodelgraph.drawer.dot.model.DotNode;

import java.util.List;
import java.util.Optional;

public class BmgNodeToDotNodeListener implements BmgNodeDfsListener {

    private List<DotNode> dotNodes;
    private List<DotEdge> dotEdges;

    public BmgNodeToDotNodeListener(@NonNull List<DotNode> nodes, @NonNull List<DotEdge> edges) {
        this.dotNodes = nodes;
        this.dotEdges = edges;
    }

    @Override
    public void onNode(List<BmgEdge> pathOfThisBmgNode, BmgNode bmgNode, Optional<BmgNode> prevBmgNodeOpt) {
        DotNode dn = findOrCreateDotNode(bmgNode);

        if (prevBmgNodeOpt.isPresent()) {
            BmgEdge incomingEdge = pathOfThisBmgNode.get(pathOfThisBmgNode.size() - 1);
            DotEdge dotEdge = toDotEdge(prevBmgNodeOpt.get(), incomingEdge);
            dotEdges.add(dotEdge);
        }
    }

    private DotNode findOrCreateDotNode(BmgNode bmgNode) {
        DotNode dotNode = dotNodes.stream().filter(dn -> dn.getBeanClass().equals(bmgNode.getBeanClass())).findAny().orElseGet(() -> {
                    DotNode newNode = toDotNode(bmgNode);
                    dotNodes.add(newNode);
                    return newNode;
                }
        );
        return dotNode;
    }

    private DotEdge toDotEdge(BmgNode prevBmgNode, BmgEdge bmgEdge) {
        DotEdge.DotEdgeBuilder builder = DotEdge.builder();

        if (bmgEdge instanceof BmgHasAEdge) {
            builder.source(findOrCreateDotNode(prevBmgNode))
                    .target(findOrCreateDotNode(bmgEdge.getEndingNode()))
                    .label(Optional.of(extractPropName((BmgHasAEdge) bmgEdge)))
                    .arrowhead("vee")
                    .style("solid");
        } else if (bmgEdge instanceof BmgParentOfEdge) {
            builder.source(findOrCreateDotNode(bmgEdge.getEndingNode()))
                    .target(findOrCreateDotNode(prevBmgNode))
                    .label(Optional.empty())
                    .arrowhead("onormal")
                    .style("solid");
        } else {
            throw new IllegalArgumentException(bmgEdge.getClass().toString());
        }

        return builder.build();
    }



    private DotNode toDotNode(BmgNode bmgNode) {
        DotNode dn = DotNode.builder()
                .name(bmgNode.getBeanClass().getSimpleName())
                .label(Optional.empty())
                .beanClass(bmgNode.getBeanClass())
                .build();
        return dn;
    }

    private String extractPropName(BmgHasAEdge edge) { //TODO: put this into some common place
        return edge.getPropName()
                +
                (edge.isMultiOccur() ? "[]" : "");
    }

}
