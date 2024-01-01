package org.beanmodelgraph.tabulizer;

import org.beanmodelgraph.constructor.model.BmgEdge;
import org.beanmodelgraph.constructor.model.BmgNode;
import org.beanmodelgraph.constructor.traverse.BmgDfsTraverser;
import org.beanmodelgraph.constructor.traverse.BmgNodeDfsListener;
import org.beanmodelgraph.tabulizer.model.BmgRow;

import java.util.ArrayList;
import java.util.List;

public class BeanModelGraphTabulizer {

    private static class NodeToRowListener implements BmgNodeDfsListener {

        private List<BmgRow> rows = new ArrayList<>();

        @Override
        public void onNode(List<BmgEdge> pathOfThisNode, BmgNode node) {
            BmgRow row = BmgRow.builder().path(pathOfThisNode).type(node.getType()).build();
            rows.add(row);
        }

        public List<BmgRow> getRows() {
            return rows;
        }
    }

    public List<BmgRow> toRows(BmgNode rootNode) {
        NodeToRowListener nodeListener = new NodeToRowListener();
        BmgDfsTraverser traverser = new BmgDfsTraverser(nodeListener);
        traverser.traverse(rootNode);
        return nodeListener.getRows();
    }

}
