package org.beanmodelgraph.testcommon.testdata;

import com.paypal.api.payments.CountryCode;
import com.paypal.api.payments.Currency;
import com.paypal.api.payments.DefinitionsLinkdescription;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import lombok.Builder;
import lombok.Getter;
import org.beanmodelgraph.testcommon.model.child.ChildPackageAnchor;
import org.beanmodelgraph.testcommon.model.parent.IA;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Builder
public class ConstructorTestParam {
    private Class<?> rootBeanClass;

    private List<String> subTypeScanBasePackages;

    private Set<Class<?>> additionalAtomicTypes;

    public static ConstructorTestParam AbcTestParam =
            ConstructorTestParam.builder()
                    .rootBeanClass(IA.class)
                    .subTypeScanBasePackages(Arrays.asList(ChildPackageAnchor.class.getPackage().getName()))
                    .additionalAtomicTypes(new HashSet<>())
                    .build();

    public static ConstructorTestParam PaypalTestParam =
            ConstructorTestParam.builder()
                    .rootBeanClass(Payment.class)
                    .subTypeScanBasePackages(Arrays.asList("com.paypal.api.payments"))
                    .additionalAtomicTypes(Arrays.asList(Currency.class, DefinitionsLinkdescription.class,
                            Links.class, CountryCode.class).stream().collect(Collectors.toSet()))
                    .build();
}
