package org.beanmodelgraph.constructor;

import lombok.NonNull;
import org.beanmodelgraph.constructor.model.BmgNode;

public interface BeanModelGraphTraverser {

    void traverse(@NonNull BmgNode rootNode);
}
