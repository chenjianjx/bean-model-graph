package org.beanmodelgraph.testcommon.model.child;


import lombok.Getter;
import org.beanmodelgraph.testcommon.model.parent.IA;

@Getter
public class ConcreteCFoo extends AbstractC {

    private float someFloat;

    private IA backToRoot;
}
