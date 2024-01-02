package org.beanmodelgraph.constructor.traverse;

import org.beanmodelgraph.constructor.model.BmgEdge;
import org.beanmodelgraph.constructor.model.BmgNode;

import java.util.List;
import java.util.Optional;

/**
 * Thread-safety:  not safe
 */
public interface BmgNodeDfsListener {
    void onNode(List<BmgEdge> pathOfThisNode, BmgNode node, Optional<BmgNode> prevNodeOpt);
}
