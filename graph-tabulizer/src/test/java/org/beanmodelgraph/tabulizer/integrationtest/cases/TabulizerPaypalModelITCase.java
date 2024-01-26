package org.beanmodelgraph.tabulizer.integrationtest.cases;

import com.paypal.api.payments.Payment;

import java.util.Arrays;
import java.util.List;


public class TabulizerPaypalModelITCase extends BmgBuildAndTabulizeITCaseBase {

    public Class<?> getRootBeanClass() {
        return Payment.class;
    }

    @Override
    public List<String> getSubTypeScanBasePackages() {
        return Arrays.asList("com.paypal.api.payments");
    }

}
