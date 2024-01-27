package org.beanmodelgraph.tabulizer.integrationtest.model;

import org.beanmodelgraph.tabulizer.model.BmgRow;
import org.ssio.api.interfaces.annotation.SsColumn;

public class BmgRowViewObject {

    @SsColumn(index = 0)
    private String propertyPath;

    @SsColumn(index = 1, name = "Type")
    private String typeSimpleName;

    public static BmgRowViewObject fromBmgRow(BmgRow bo) {
        BmgRowViewObject vo = new BmgRowViewObject();
        vo.propertyPath = bo.getPathAsString();
        vo.typeSimpleName = bo.getType().getSimpleName();
        return vo;
    }

    public String getPropertyPath() {
        return propertyPath;
    }

    public String getTypeSimpleName() {
        return typeSimpleName;
    }


}
