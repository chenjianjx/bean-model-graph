package org.beanmodelgraph.constructor.traverse;

import lombok.NonNull;
import org.beanmodelgraph.constructor.BeanModelGraphTraverser;
import org.beanmodelgraph.constructor.model.BmgEdge;
import org.beanmodelgraph.constructor.model.BmgNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class BmgDfsTraverser implements BeanModelGraphTraverser {

    private final BmgNodeDfsListener nodeListener;

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

        for (BmgEdge edgeToNextNode : thisNode.getEdges()) {
            doTraverse(pathOfThisNode, Optional.of(edgeToNextNode), edgeToNextNode.getEndingNode());
        }
    }

}
