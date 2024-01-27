package org.beanmodelgraph.tabulizer.model;


import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.beanmodelgraph.constructor.model.BmgEdge;
import org.beanmodelgraph.tabulizer.render.BmgRowPathRenderer;
import org.beanmodelgraph.tabulizer.render.DefaultBmgRowPathRenderer;

import java.util.List;

@Value
@Builder
public class BmgRow {

    private static BmgRowPathRenderer defaultRowPathRenderer = new DefaultBmgRowPathRenderer();

    /**
     * The path from root to the row this property represents
     */
    @NonNull
    private List<BmgEdge> path;

    @NonNull
    private Class<?> type;

    /**
     * Render the path in default way. If you want to render it your own way,
     * 1. Don't use this method
     * 2. Create your implementation {@link BmgRowPathRenderer} and call its render() method
     *
     */
    public String getPathAsString(){
        return defaultRowPathRenderer.render(this.path);
    }
}