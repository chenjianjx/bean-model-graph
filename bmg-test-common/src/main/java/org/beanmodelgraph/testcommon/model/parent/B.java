package org.beanmodelgraph.testcommon.model.parent;

import lombok.Getter;
import org.beanmodelgraph.testcommon.model.child.ConcreteCFoo;

@Getter
public class B {


    private IC c;
    private ConcreteCFoo zzzDirectCFoo;

}