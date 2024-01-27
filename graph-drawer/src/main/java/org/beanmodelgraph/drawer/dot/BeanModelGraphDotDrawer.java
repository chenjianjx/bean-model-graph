package org.beanmodelgraph.drawer.dot;

import lombok.NonNull;
import org.beanmodelgraph.constructor.model.BmgNode;
import org.beanmodelgraph.constructor.traverse.BmgDfsTraverser;
import org.beanmodelgraph.drawer.dot.listener.BmgNodeToDotNodeListener;
import org.beanmodelgraph.drawer.dot.model.DotGraph;
import org.beanmodelgraph.drawer.dot.model.DotNode;
import org.beanmodelgraph.drawer.dot.render.DotGraphRenderer;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Can be used as singleton
 */
public class BeanModelGraphDotDrawer {

    @NonNull
    private DotGraphRenderer dotGraphRenderer;

    public BeanModelGraphDotDrawer(DotGraphRenderer dotGraphRenderer) {
        this.dotGraphRenderer = dotGraphRenderer;
    }

    public String toDotScript(@NonNull BmgNode rootNode) {
        DotGraph dg = toDotGraph(rootNode);
        return dotGraphRenderer.render(dg);
    }

    public DotGraph toDotGraph(@NonNull BmgNode rootNode) {
        DotGraph dg = new DotGraph();
        BmgNodeToDotNodeListener nodeListener = new BmgNodeToDotNodeListener(dg.getNodes(), dg.getEdges());
        BmgDfsTraverser traverser = new BmgDfsTraverser(nodeListener);
        traverser.traverse(rootNode);
        distinguishNameTwins(dg.getNodes());
        return dg;
    }

    private void distinguishNameTwins(@NonNull List<DotNode> dotNodes) {
        Map<String, List<DotNode>> nameToNodes = dotNodes.stream().collect(Collectors.groupingBy(DotNode::getName));
        nameToNodes.values()
                .stream()
                .filter(nodes -> nodes.size() > 1)
                .forEach(nodes ->
                        nodes.forEach(node ->
                                {
                                    node.setLabel(Optional.of(node.getBeanClass().getName()));
                                    node.setName("\"" + node.getBeanClass().getName() + "\""); //TODO: use doubleQuote
                                }
                        )
                );
    }

}
