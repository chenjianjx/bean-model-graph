package org.beanmodelgraph.drawer.integrationtest.cases;

import org.beanmodelgraph.drawer.integrationtest.ownmodel.One;
import org.beanmodelgraph.drawer.integrationtest.ownmodel.OneTwoThreePackageAnchor;

import java.util.Arrays;
import java.util.List;

public class Drawer123ITCase extends BmgBuildAndDrawITCaseBase {
    @Override
    public Class<One> getRootBeanClass() {
        return One.class;
    }

    @Override
    public List<String> getSubTypeScanBasePackages() {
        return Arrays.asList(OneTwoThreePackageAnchor.class.getPackage().getName());
    }
}
