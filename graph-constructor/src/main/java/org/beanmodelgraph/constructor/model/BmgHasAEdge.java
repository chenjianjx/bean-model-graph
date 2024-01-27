package org.beanmodelgraph.constructor.model;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@AllArgsConstructor
@ToString
public class BmgHasAEdge extends BmgEdge {

    @NonNull
    private String propName;
    private boolean multiOccur;

    @Override
    public BmgEdgeColor getColor() {
        return BmgEdgeColor.HAS_A;
    }

    public String simpleDisplay() {
        return this.getPropName() + (this.isMultiOccur() ? "[]" : "");
    }
}
