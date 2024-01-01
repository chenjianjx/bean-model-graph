package org.beanmodelgraph.constructor.traverse;

import org.beanmodelgraph.constructor.model.BmgEdge;
import org.beanmodelgraph.constructor.model.BmgNode;

import java.util.List;

public interface BmgNodeDfsListener {
    void onNode(List<BmgEdge> pathOfThisNode, BmgNode node);
}
