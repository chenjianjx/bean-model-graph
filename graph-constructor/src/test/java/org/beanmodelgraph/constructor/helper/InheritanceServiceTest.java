package org.beanmodelgraph.constructor.helper;

import org.beanmodelgraph.testcommon.model.child.AbstractC;
import org.beanmodelgraph.testcommon.model.child.ConcreteCBar;
import org.beanmodelgraph.testcommon.model.child.ConcreteCFoo;
import org.beanmodelgraph.testcommon.model.parent.IC;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InheritanceServiceTest {

    InheritanceService scanner = new InheritanceService();

    @Test
    void getSubTypes() {
        List<String> packagesToScan = Arrays.asList(AbstractC.class.getPackage().getName());
        assertEquals(new HashSet<Class<?>>(Arrays.asList(AbstractC.class, ConcreteCFoo.class, ConcreteCBar.class)), scanner.getSubTypes(IC.class, packagesToScan));

        //direct inheritance by implementation
        assertEquals(new HashSet<Class<?>>(Arrays.asList(AbstractC.class)), scanner.getDirectSubTypes(IC.class, packagesToScan));

        //direct inheritance by extending
        assertEquals(new HashSet<Class<?>>(Arrays.asList(ConcreteCFoo.class, ConcreteCBar.class)), scanner.getDirectSubTypes(AbstractC.class, packagesToScan));
    }
}