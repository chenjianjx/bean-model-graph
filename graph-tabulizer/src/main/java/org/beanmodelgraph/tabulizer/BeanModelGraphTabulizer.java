package org.beanmodelgraph.tabulizer;

import org.beanmodelgraph.constructor.model.BmgEdge;
import org.beanmodelgraph.constructor.model.BmgGraph;
import org.beanmodelgraph.constructor.model.BmgNode;
import org.beanmodelgraph.constructor.traverse.BmgDfsTraverser;
import org.beanmodelgraph.constructor.traverse.BmgNodeDfsListener;
import org.beanmodelgraph.tabulizer.model.BmgRow;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BeanModelGraphTabulizer {

    private static class NodeToRowListener implements BmgNodeDfsListener {

        private List<BmgRow> rows = new ArrayList<>();

        @Override
        public void onNode(List<BmgEdge> pathOfThisNode, BmgNode node, Optional<BmgNode> prevNodeOpt) {
            BmgRow row = BmgRow.builder().path(pathOfThisNode).type(node.getBeanClass()).build();
            rows.add(row);
        }

        public List<BmgRow> getRows() {
            return rows;
        }
    }


    public List<BmgRow> toRows(BmgGraph bmgGraph) {
        NodeToRowListener nodeListener = new NodeToRowListener();
        BmgDfsTraverser traverser = new BmgDfsTraverser(nodeListener);
        traverser.traverse(bmgGraph.getRootNode());
        List<BmgRow> rows = nodeListener.getRows();
//        if(propertyPathOnly){
//            rows = rows.stream().filter(r -> isPropertyPath(r.getPath())).collect(Collectors.toList());
//        }
        return rows;
    }

//    private boolean isPropertyPath(List<BmgEdge> path) {
//        return path.size() > 0 && (path.get(path.size() - 1) instanceof BmgHasAEdge);
//    }

}
