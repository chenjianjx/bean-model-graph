package org.beanmodelgraph.tabulizer.render;

import org.beanmodelgraph.constructor.model.BmgEdge;

import java.util.List;

public interface BmgRowPathRenderer {

    String render(List<BmgEdge> path);
}
