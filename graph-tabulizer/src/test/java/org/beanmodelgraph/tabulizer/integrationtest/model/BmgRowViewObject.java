package org.beanmodelgraph.tabulizer.integrationtest.model;

import lombok.Builder;
import lombok.Value;
import org.beanmodelgraph.constructor.model.BmgEdge;
import org.beanmodelgraph.constructor.model.BmgHasAEdge;
import org.beanmodelgraph.tabulizer.model.BmgRow;
import org.ssio.api.interfaces.annotation.SsColumn;

import java.util.Objects;
import java.util.stream.Collectors;


@Builder
@Value
public class BmgRowViewObject {

    @SsColumn(index = 0)
    private String path;

    @SsColumn(index = 1)
    private String typeSimpleName;

    public static BmgRowViewObject fromBizObject(BmgRow bo) {
        return BmgRowViewObject.builder()
                .path(bo.getPath().stream().map(edge -> extractPropName((BmgHasAEdge) edge)).collect(Collectors.joining(".")))
                .typeSimpleName(bo.getType().getSimpleName()).build();
    }

    private static String extractPropName(BmgHasAEdge edge) {
        return edge.getPropName();
    }
}
