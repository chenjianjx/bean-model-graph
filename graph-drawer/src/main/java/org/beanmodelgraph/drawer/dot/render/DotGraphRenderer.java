package org.beanmodelgraph.drawer.dot.render;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.beanmodelgraph.drawer.dot.model.DotGraph;

import java.util.Optional;

public interface DotGraphRenderer {

    @Getter
    @Builder
    public static class RenderOptions {
        private boolean hideAtomicTypes;
    }

    String render(@NonNull DotGraph dotGraph, @NonNull Optional<RenderOptions> renderOptions);
}
