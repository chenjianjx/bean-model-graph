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
        StringBuilder result = new StringBuilder();
        for (BmgEdge edge : path) {
            if (edge instanceof BmgHasAEdge) {
                result.append(".").append(extractPropName((BmgHasAEdge) edge));
            } else if (edge instanceof BmgParentOfEdge) {
                result.append("<").append(edge.getEndingNode().getBeanClass().getSimpleName()).append(">");
            } else {
                throw new IllegalArgumentException(edge.getClass().toString());
            }
        }
        return result.toString()
                .replace("><", "|"); //<AbstractC><ConcreteCBar> => <AbstractC|ConcreteCBar>, hacky but it works
    }

    private String extractPropName(BmgHasAEdge edge) {
        return edge.getPropName()
                +
                (edge.isCollectionProp() ? "[]" : "");
    }
}
