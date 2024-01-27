package org.beanmodelgraph.drawer.dot.model;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.Optional;

@Data
@Builder
public class DotNode {

    @NonNull
    private String name;

    @NonNull
    private Optional<String> label;

    private boolean atomicType;

    /**********The following are non-dot attributes ********************/
    @NonNull
    private final Class<?> beanClass;

}