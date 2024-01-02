package org.beanmodelgraph.constructor.traverse;

import lombok.NonNull;
import org.beanmodelgraph.constructor.BeanModelGraphTraverser;
import org.beanmodelgraph.constructor.model.BmgEdge;
import org.beanmodelgraph.constructor.model.BmgNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class BmgDfsTraverser implements BeanModelGraphTraverser {
    private final BmgNodeDfsListener nodeListener;

    /**
     * has traversed this node - not only for itself, but also for its next nodes
     */
    private Set<BmgNode> expandedNodes = new HashSet<>();

    public BmgDfsTraverser(@NonNull BmgNodeDfsListener nodeListener) {
        this.nodeListener = nodeListener;
    }

    @Override
    public void traverse(@NonNull BmgNode rootNode) {
        doTraverse(Collections.emptyList(), Optional.empty(), rootNode);
    }

    private void doTraverse(List<BmgEdge> pathOfPrevNode, Optional<BmgEdge> edgeToThisNode, BmgNode thisNode) {


        List<BmgEdge> pathOfThisNode = new ArrayList<>();
        pathOfThisNode.addAll(pathOfPrevNode);
        edgeToThisNode.ifPresent(eo -> pathOfThisNode.add(eo));

        nodeListener.onNode(pathOfThisNode, thisNode);

        if (expandedNodes.contains(thisNode)) {
            return;
        } else {
            expandedNodes.add(thisNode);
            for (BmgEdge edgeToNextNode : thisNode.getEdges()) {
                doTraverse(pathOfThisNode, Optional.of(edgeToNextNode), edgeToNextNode.getEndingNode());
            }
        }
    }

}
