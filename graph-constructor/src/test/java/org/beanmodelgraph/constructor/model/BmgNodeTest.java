package org.beanmodelgraph.constructor.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class BmgNodeTest {

    @Test
    public void nonNullTest_positive(){
        new BmgNode(String.class, new ArrayList<>());
    }


    @Test
    public void nonNullTest_nullEdges(){
        Assertions.assertThrows(NullPointerException.class, () ->
                new BmgNode(String.class, null)
        );

    }
}