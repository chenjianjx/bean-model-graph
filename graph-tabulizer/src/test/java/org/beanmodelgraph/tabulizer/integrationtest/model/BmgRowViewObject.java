package org.beanmodelgraph.tabulizer.integrationtest.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.beanmodelgraph.constructor.model.BmgEdge;
import org.beanmodelgraph.constructor.model.BmgHasAEdge;
import org.beanmodelgraph.constructor.model.BmgParentOfEdge;
import org.beanmodelgraph.tabulizer.model.BmgRow;
import org.ssio.api.interfaces.annotation.SsColumn;

import java.util.List;
import java.util.stream.Collectors;


@Builder
@Value
public class BmgRowViewObject {

    @SsColumn(index = 0)
    private String propertyPath;

    @SsColumn(index = 1, name = "Type")
    private String typeSimpleName;

    public static BmgRowViewObject fromBizObject(BmgRow bo) {
        return BmgRowViewObject.builder()
                .propertyPath(pathToString(bo.getPath()))
                .typeSimpleName(bo.getType().getSimpleName()).build();
    }

    private static String pathToString(List<BmgEdge> path) {
        StringBuilder result = new StringBuilder();
        for (BmgEdge edge : path) {
            if (edge instanceof BmgHasAEdge) {
                result.append(".").append(extractPropName((BmgHasAEdge) edge));
            } else if (edge instanceof BmgParentOfEdge) {
                result.append("<").append(edge.getEndingNode().getType().getSimpleName()).append(">");
            } else {
                throw new IllegalArgumentException(edge.getClass().toString());
            }
        }

        return result.toString();
    }

    private static String extractPropName(BmgHasAEdge edge) {
        return edge.getPropName()
                +
                (edge.isCollectionProp() ? "[]" : "");
    }
}
