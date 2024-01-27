package org.beanmodelgraph.tabulizer.integrationtest.cases;

import com.paypal.api.payments.CountryCode;
import com.paypal.api.payments.Currency;
import com.paypal.api.payments.DefinitionsLinkdescription;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class TabulizerPaypalModelITCase extends BmgBuildAndTabulizeITCaseBase {

    public Class<?> getRootBeanClass() {
        return Payment.class;
    }

    @Override
    public List<String> getSubTypeScanBasePackages() {
        return Arrays.asList("com.paypal.api.payments");
    }

    @Override
    public Set<Class<?>> getAdditionalAtomicTypes() {
        return Arrays.asList(Currency.class, DefinitionsLinkdescription.class,
                Links.class, CountryCode.class).stream().collect(Collectors.toSet());
    }

}
