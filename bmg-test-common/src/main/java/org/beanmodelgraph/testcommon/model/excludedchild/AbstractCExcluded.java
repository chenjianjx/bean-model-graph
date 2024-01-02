package org.beanmodelgraph.testcommon.model.excludedchild;

import org.beanmodelgraph.testcommon.model.parent.IC;


public abstract class AbstractCExcluded implements IC {
    @Override
    public String getSomeString() {
        return null;
    }
}
