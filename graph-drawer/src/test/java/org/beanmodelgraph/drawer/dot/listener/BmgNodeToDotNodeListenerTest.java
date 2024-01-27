package org.beanmodelgraph.drawer.dot.listener;

import org.beanmodelgraph.constructor.model.BmgEdge;
import org.beanmodelgraph.constructor.model.BmgEdgeColor;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class BmgNodeToDotNodeListenerTest {

    private static final class AlienBmgEdge extends BmgEdge {
        @Override
        public BmgEdgeColor getColor() {
            return null;
        }
    }

    BmgNodeToDotNodeListener listener = new BmgNodeToDotNodeListener();

    @Test
    void toDotEdge_unsupportedEdgeType() {
        assertThrows(IllegalArgumentException.class, () -> listener.toDotEdge(null, new AlienBmgEdge()));
    }
}