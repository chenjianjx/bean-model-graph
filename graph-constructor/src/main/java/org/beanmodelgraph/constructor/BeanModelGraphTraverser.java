package org.beanmodelgraph.constructor;

import lombok.NonNull;
import org.beanmodelgraph.constructor.model.BmgNode;

/**
 * Thread-safety:  not safe
 */
public interface BeanModelGraphTraverser {

    void traverse(@NonNull BmgNode rootNode);
}
