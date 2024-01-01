package org.beanmodelgraph.tabulizer.integrationtest.model;

import lombok.Builder;
import lombok.Data;
import lombok.Value;
import org.beanmodelgraph.tabulizer.model.BmgRow;
import org.ssio.api.interfaces.annotation.SsColumn;


@Builder
@Value
public class BmgRowViewObject {

    @SsColumn(index = 0)
    private String typeSimpleName;

    public static BmgRowViewObject fromBizObject(BmgRow bo) {
        return BmgRowViewObject.builder().typeSimpleName(bo.getType().getSimpleName()).build();
    }
}
