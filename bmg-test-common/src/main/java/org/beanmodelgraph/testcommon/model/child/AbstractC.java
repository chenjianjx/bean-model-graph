package org.beanmodelgraph.testcommon.model.child;

import lombok.Getter;
import org.beanmodelgraph.testcommon.model.parent.IC;

public abstract class AbstractC implements IC {
    @Override
    public String getSomeString() {
        return null;
    }

    @Getter
    private boolean someBoolean;
}
