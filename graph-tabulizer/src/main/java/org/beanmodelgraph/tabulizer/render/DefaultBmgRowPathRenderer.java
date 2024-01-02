package org.beanmodelgraph.tabulizer.render;

import org.beanmodelgraph.constructor.model.BmgEdge;
import org.beanmodelgraph.constructor.model.BmgHasAEdge;
import org.beanmodelgraph.constructor.model.BmgParentOfEdge;

import java.util.List;

/***
 * stateless
 */
public class DefaultBmgRowPathRenderer implements BmgRowPathRenderer {
    @Override
    public String render(List<BmgEdge> path) {
        StringBuilder resultBuilder = new StringBuilder();
        for (BmgEdge edge : path) {
            if (edge instanceof BmgHasAEdge) {
                resultBuilder.append(".").append(extractPropName((BmgHasAEdge) edge));
            } else if (edge instanceof BmgParentOfEdge) {
                resultBuilder.append("<").append(edge.getEndingNode().getBeanClass().getSimpleName()).append(">");
            } else {
                throw new IllegalArgumentException(edge.getClass().toString());
            }
        }
        String result = resultBuilder.toString();
        result = result.replace("><", "|"); //<AbstractC><ConcreteCBar> => <AbstractC|ConcreteCBar>, hacky but it works
        if (!result.contains(".")) {
            result = result + ".";  //works for root node and its PARENT_OF ending nodes
        }
        return result;
    }

    private String extractPropName(BmgHasAEdge edge) {
        return edge.getPropName()
                +
                (edge.isMultiOccur() ? "[]" : "");
    }
}
