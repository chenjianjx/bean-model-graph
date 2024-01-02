package org.beanmodelgraph.testcommon.model.parent;

import lombok.Getter;

import java.util.List;

@Getter
public class A {

    private B b1;
    private B b2;
    private List<Beta> betaList;
    private List<Beta> betaArray;

}
