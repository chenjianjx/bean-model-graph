package org.beanmodelgraph.tabulizer.integrationtest.cases;

import org.beanmodelgraph.testcommon.model.child.ChildPackageAnchor;
import org.beanmodelgraph.testcommon.model.parent.IA;

import java.util.Arrays;
import java.util.List;

public class TabulizerAbcITCase extends BmgBuildAndTabulizeITCaseBase {
    @Override
    public Class<IA> getRootBeanClass() {
        return IA.class;
    }

    @Override
    public List<String> getSubTypeScanBasePackages() {
        return Arrays.asList(ChildPackageAnchor.class.getPackage().getName());
    }
}
