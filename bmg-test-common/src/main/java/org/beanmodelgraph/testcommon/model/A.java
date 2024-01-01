package org.beanmodelgraph.testcommon.model;

import lombok.Value;

import java.util.List;

@Value
public class A {

    private B b1;
    private B b2;
    private List<Beta> betaList;
    private List<Beta> betaArray;

}
