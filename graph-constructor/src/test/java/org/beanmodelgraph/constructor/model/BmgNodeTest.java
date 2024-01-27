package org.beanmodelgraph.constructor.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BmgNodeTest {


    @Test
    void equalsTest_sameObject() {
        BmgNode node = new BmgNode(String.class, true);
        node.setEdges(new ArrayList<>());
        assertTrue(node.equals(node));
    }

    @Test
    void equalsTest_sameContent() {
        assertFalse(new BmgNode(String.class, true).equals(new BmgNode(String.class, true)));
    }
}