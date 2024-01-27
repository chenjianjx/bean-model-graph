package org.beanmodelgraph.tabulizer.render;

import org.beanmodelgraph.constructor.model.BmgEdge;
import org.beanmodelgraph.constructor.model.BmgEdgeColor;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertThrows;

class DefaultBmgRowPathRendererTest {

    DefaultBmgRowPathRenderer renderer = new DefaultBmgRowPathRenderer();

    private static final class AlienBmgEdge extends BmgEdge {

        @Override
        public BmgEdgeColor getColor() {
            return null;
        }
    }

    @Test
    void render_unsupportedEdgeType() {
        assertThrows(IllegalArgumentException.class, () -> renderer.render(
                Arrays.asList(new AlienBmgEdge())
        ));
    }
}