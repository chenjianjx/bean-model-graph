package org.beanmodelgraph.testcommon.model.child;

import lombok.Getter;
import org.beanmodelgraph.testcommon.model.parent.B;
import org.beanmodelgraph.testcommon.model.parent.Beta;
import org.beanmodelgraph.testcommon.model.parent.IA;

import java.util.List;
import java.util.Map;

@Getter
public class A implements IA {

    private B b1;
    private B b2;
    private Beta beta;
    private List<Beta> mmmBetaList;
    private Beta[] mmmBetaArray;

    private Map<String, Float> someMap;

    private List<List<Beta>> zzzListOfBetaList;
    private List<Beta[]> zzzListOfBetaArray;
    private List<Beta>[] zzzArrayOfBetaList;
    private Beta[][] zzzArrayOfBetaArray;

}
