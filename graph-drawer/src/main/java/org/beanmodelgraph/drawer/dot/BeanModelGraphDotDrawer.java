package org.beanmodelgraph.drawer.dot;

import lombok.NonNull;
import org.beanmodelgraph.constructor.model.BmgGraph;
import org.beanmodelgraph.constructor.traverse.BmgDfsTraverser;
import org.beanmodelgraph.drawer.dot.listener.BmgNodeToDotNodeListener;
import org.beanmodelgraph.drawer.dot.model.DotGraph;
import org.beanmodelgraph.drawer.dot.model.DotNode;
import org.beanmodelgraph.drawer.dot.render.DotGraphRenderer;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.beanmodelgraph.constructor.util.BmgStringUtils.doubleQuote;

/**
 * Can be used as singleton
 */
public class BeanModelGraphDotDrawer {

    @NonNull
    private DotGraphRenderer dotGraphRenderer;

    public BeanModelGraphDotDrawer(DotGraphRenderer dotGraphRenderer) {
        this.dotGraphRenderer = dotGraphRenderer;
    }

    public String toDotScript(@NonNull BmgGraph bmgGraph) {
        DotGraph dg = toDotGraph(bmgGraph);
        return dotGraphRenderer.render(dg, Optional.empty());
    }

    public String toDotScript(@NonNull BmgGraph bmgGraph, @NonNull Optional<DotGraphRenderer.RenderOptions> renderOptions) {
        DotGraph dg = toDotGraph(bmgGraph);
        return dotGraphRenderer.render(dg, renderOptions);
    }

    public DotGraph toDotGraph(@NonNull BmgGraph bmgGraph) {

        BmgNodeToDotNodeListener nodeListener = new BmgNodeToDotNodeListener();
        BmgDfsTraverser traverser = new BmgDfsTraverser(nodeListener);
        traverser.traverse(bmgGraph.getRootNode());
        distinguishNameTwins(nodeListener.getDotNodes());

        DotGraph dg = DotGraph.builder()
                .nodes(nodeListener.getDotNodes())
                .edges(nodeListener.getDotEdges())
                .build();
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
                                    node.setName(doubleQuote(node.getBeanClass().getName()));
                                }
                        )
                );
    }

}
