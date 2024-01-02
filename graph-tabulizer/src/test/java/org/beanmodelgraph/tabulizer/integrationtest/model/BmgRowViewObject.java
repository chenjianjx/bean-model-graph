package org.beanmodelgraph.tabulizer.integrationtest.model;

import lombok.Builder;
import lombok.Value;
import org.ssio.api.interfaces.annotation.SsColumn;


@Builder
@Value
public class BmgRowViewObject {

    @SsColumn(index = 0)
    private String propertyPath;

    @SsColumn(index = 1, name = "Type")
    private String typeSimpleName;

}
