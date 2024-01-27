package org.beanmodelgraph.testcommon.model.parent;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.beanmodelgraph.testcommon.model.child.ConcreteCFoo;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class B {


    private IC c;
    private ConcreteCFoo zzzDirectCFoo;

}
