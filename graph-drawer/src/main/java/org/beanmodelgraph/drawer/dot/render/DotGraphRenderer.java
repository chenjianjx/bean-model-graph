package org.beanmodelgraph.drawer.dot.render;

import lombok.NonNull;
import org.beanmodelgraph.drawer.dot.model.DotGraph;

public interface DotGraphRenderer {
    String render(@NonNull DotGraph dotGraph);
}
