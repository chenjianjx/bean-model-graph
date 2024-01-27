package org.beanmodelgraph.testcommon.model.child;

import lombok.Getter;
import lombok.Setter;
import org.beanmodelgraph.testcommon.model.parent.IC;

public abstract class AbstractC implements IC {
    @Override
    public String getSomeString() {
        return null;
    }

    @Getter
    private boolean someBoolean;

    @Setter
    private String propBySetter;
}
