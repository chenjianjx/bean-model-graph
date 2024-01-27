package org.beanmodelgraph.constructor.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BmgNodeTest {

    @Test
    public void nonNullTest_positive() {
        BmgNode node = new BmgNode(String.class);
        node.setEdges(new ArrayList<>());
    }

    @Test
    public void nonNullTest_nullType() {
        Assertions.assertThrows(NullPointerException.class, () ->
                new BmgNode(null)
        );
    }

    @Test
    public void nonNullTest_nullEdges() {
        Assertions.assertThrows(NullPointerException.class, () ->
                {
                    BmgNode node = new BmgNode(String.class);
                    node.setEdges(null);
                }
        );
    }

    @Test
    public void equalsTest_sameObject() {
        BmgNode node = new BmgNode(String.class);
        node.setEdges(new ArrayList<>());
        assertTrue(node.equals(node));
    }

    @Test
    public void equalsTest_sameContent() {
        assertFalse(new BmgNode(String.class).equals(new BmgNode(String.class)));
    }
}