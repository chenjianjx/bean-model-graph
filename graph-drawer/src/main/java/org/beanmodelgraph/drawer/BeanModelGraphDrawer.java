package org.beanmodelgraph.drawer;

import org.beanmodelgraph.constructor.model.BmgEdge;
import org.beanmodelgraph.constructor.model.BmgHasAEdge;
import org.beanmodelgraph.constructor.model.BmgNode;
import org.beanmodelgraph.constructor.model.BmgParentOfEdge;
import org.beanmodelgraph.constructor.traverse.BmgDfsTraverser;
import org.beanmodelgraph.constructor.traverse.BmgNodeDfsListener;
import org.beanmodelgraph.drawer.model.DotEdge;
import org.beanmodelgraph.drawer.model.DotGraph;
import org.beanmodelgraph.drawer.model.DotNode;
import org.beanmodelgraph.drawer.renderer.DotGraphRenderer;

import java.util.List;
import java.util.Optional;

/**
 * Can be used as singleton
 */
public class BeanModelGraphDrawer {

    private DotGraphRenderer dotGraphRenderer;

    public BeanModelGraphDrawer(DotGraphRenderer dotGraphRenderer) {
        this.dotGraphRenderer = dotGraphRenderer;
    }

    private static class BmgNodeToDotNodeListener implements BmgNodeDfsListener {

        private List<DotNode> dotNodes;
        private List<DotEdge> dotEdges;

        public BmgNodeToDotNodeListener(List<DotNode> nodes, List<DotEdge> edges) {
            this.dotNodes = nodes;
            this.dotEdges = edges;
        }

        @Override
        public void onNode(List<BmgEdge> pathOfThisBmgNode, BmgNode bmgNode, Optional<BmgNode> prevBmgNodeOpt) {
            DotNode dotNode = toDotNode(bmgNode);
            if (!dotNodes.contains(dotNode)) {
                dotNodes.add(dotNode);
            }

            if (prevBmgNodeOpt.isPresent()) {
                BmgEdge incomingEdge = pathOfThisBmgNode.get(pathOfThisBmgNode.size() - 1);
                DotEdge dotEdge = toDotEdge(prevBmgNodeOpt.get(), incomingEdge);
                dotEdges.add(dotEdge);
            }
        }

        private DotEdge toDotEdge(BmgNode prevBmgNode, BmgEdge bmgEdge) {
            DotEdge.DotEdgeBuilder builder = DotEdge.builder();


            if (bmgEdge instanceof BmgHasAEdge) {
                builder.source(toDotNode(prevBmgNode).getName())
                        .target(toDotNode(bmgEdge.getEndingNode()).getName());
                builder.label(Optional.of(extractPropName((BmgHasAEdge) bmgEdge)));
                builder.arrowhead("vee");
                builder.style("solid");
            } else if (bmgEdge instanceof BmgParentOfEdge) {
                builder.source(toDotNode(bmgEdge.getEndingNode()).getName())
                        .target(toDotNode(prevBmgNode).getName());
                builder.label(Optional.empty());
                builder.arrowhead("onormal");
                builder.style("solid");
            } else {
                throw new IllegalArgumentException(bmgEdge.getClass().toString());
            }
            return builder.build();
        }

        private DotNode toDotNode(BmgNode bmgNode) {
            DotNode dn = new DotNode(//TODO: what if same class name, different package name?
                    bmgNode.getBeanClass().getSimpleName(),
                    bmgNode.getBeanClass().getSimpleName()
            );
            return dn;
        }

        private String extractPropName(BmgHasAEdge edge) { //TODO: put this into some common place
            return edge.getPropName()
                    +
                    (edge.isMultiOccur() ? "[]" : "");
        }

    }

    public String toDotScript(BmgNode rootNode) {
        DotGraph dg = new DotGraph();
        BmgNodeToDotNodeListener nodeListener = new BmgNodeToDotNodeListener(dg.getNodes(), dg.getEdges());
        BmgDfsTraverser traverser = new BmgDfsTraverser(nodeListener);
        traverser.traverse(rootNode);
        return dotGraphRenderer.render(dg);
    }


}
