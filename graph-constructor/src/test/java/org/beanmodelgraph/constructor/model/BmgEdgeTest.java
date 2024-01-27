package org.beanmodelgraph.constructor.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BmgEdgeTest {

    @Test
    void getColor() {
        assertEquals(BmgEdgeColor.HAS_A, new BmgHasAEdge("someName", false).getColor());
        assertEquals(BmgEdgeColor.PARENT_OF, new BmgParentOfEdge().getColor());
    }
}