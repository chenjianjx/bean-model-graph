package org.beanmodelgraph.drawer.integrationtest.cases;

import com.paypal.api.payments.Payment;

import java.util.Arrays;
import java.util.List;


public class DrawerPaypalModelITCase extends BmgBuildAndDrawITCaseBase {

    public Class<?> getRootBeanClass() {
        return Payment.class;
    }

    @Override
    public List<String> getSubTypeScanBasePackages() {
        return Arrays.asList("com.paypal.api.payments"); //TODO: move this package name to some common place
    }

}
