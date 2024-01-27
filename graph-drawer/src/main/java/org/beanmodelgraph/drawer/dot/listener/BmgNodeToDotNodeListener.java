package org.beanmodelgraph.drawer.dot.listener;

import lombok.Getter;
import org.beanmodelgraph.constructor.model.BmgEdge;
import org.beanmodelgraph.constructor.model.BmgHasAEdge;
import org.beanmodelgraph.constructor.model.BmgNode;
import org.beanmodelgraph.constructor.model.BmgParentOfEdge;
import org.beanmodelgraph.constructor.traverse.BmgNodeDfsListener;
import org.beanmodelgraph.drawer.dot.model.DotEdge;
import org.beanmodelgraph.drawer.dot.model.DotNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * one time use
 */
public class BmgNodeToDotNodeListener implements BmgNodeDfsListener {

    @Getter
    private List<DotNode> dotNodes = new ArrayList<>();

    @Getter
    private List<DotEdge> dotEdges = new ArrayList<>();





    @Override
    public void onNode(List<BmgEdge> pathOfThisBmgNode, BmgNode bmgNode, Optional<BmgNode> prevBmgNodeOpt) {
        findOrCreateDotNode(bmgNode);

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

    DotEdge toDotEdge(BmgNode prevBmgNode, BmgEdge bmgEdge) {
        DotEdge.DotEdgeBuilder builder = DotEdge.builder();

        if (bmgEdge instanceof BmgHasAEdge) {
            builder.source(findOrCreateDotNode(prevBmgNode))
                    .target(findOrCreateDotNode(bmgEdge.getEndingNode()))
                    .label(Optional.of(((BmgHasAEdge) bmgEdge).simpleDisplay()))
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
                .atomicType(bmgNode.isAtomicType())
                .build();
        return dn;
    }



}
