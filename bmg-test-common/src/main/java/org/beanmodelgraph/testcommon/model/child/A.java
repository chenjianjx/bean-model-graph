package org.beanmodelgraph.testcommon.model.child;

import lombok.Getter;
import org.beanmodelgraph.testcommon.model.parent.B;
import org.beanmodelgraph.testcommon.model.parent.Beta;
import org.beanmodelgraph.testcommon.model.parent.IA;

import java.util.List;
import java.util.Map;

@Getter
public class A implements IA {

    private B b1;
    private B b2;
    private List<Beta> betaList;
    private List<Beta> betaArray;

    private Map<B, Beta> someMap;

}
