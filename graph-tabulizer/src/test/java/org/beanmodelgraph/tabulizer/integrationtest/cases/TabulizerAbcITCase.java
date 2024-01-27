package org.beanmodelgraph.tabulizer.integrationtest.cases;

import org.beanmodelgraph.testcommon.model.child.ChildPackageAnchor;
import org.beanmodelgraph.testcommon.model.parent.IA;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TabulizerAbcITCase extends BmgBuildAndTabulizeITCaseBase {
    @Override
    public Class<IA> getRootBeanClass() {
        return IA.class;
    }

    @Override
    public List<String> getSubTypeScanBasePackages() {
        return Arrays.asList(ChildPackageAnchor.class.getPackage().getName());
    }

    @Override
    public Set<Class<?>> getAdditionalAtomicTypes() {
        return new HashSet<>();
    }
}
