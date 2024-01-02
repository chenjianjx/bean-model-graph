package org.beanmodelgraph.constructor.helper;

import lombok.SneakyThrows;
import org.beanmodelgraph.testcommon.model.child.AbstractC;
import org.beanmodelgraph.testcommon.model.child.ChildPackageAnchor;
import org.beanmodelgraph.testcommon.model.child.ConcreteCBar;
import org.beanmodelgraph.testcommon.model.child.ConcreteCFoo;
import org.beanmodelgraph.testcommon.model.parent.IC;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InheritanceServiceTest {

    InheritanceService service = new InheritanceService();

    @Test
    void getSubTypes() {
        List<String> packagesToScan = Arrays.asList(ChildPackageAnchor.class.getPackage().getName());
        assertEquals(asSet(AbstractC.class, ConcreteCFoo.class, ConcreteCBar.class), service.getSubTypes(IC.class, packagesToScan));

        //direct inheritance by implementation
        assertEquals(asSet(AbstractC.class), service.getDirectSubTypes(IC.class, packagesToScan));

        //direct inheritance by extending
        assertEquals(asSet(ConcreteCFoo.class, ConcreteCBar.class), service.getDirectSubTypes(AbstractC.class, packagesToScan));
    }

    @SneakyThrows
    @Test
    void isMethodInheritedFrom_insideHierarchy() {
        String methodName = "getSomeString";

        assertFalse(service.isMethodInheritedFrom(IC.class, IC.class.getMethod(methodName), new HashSet<>()));
        //not from self
        assertFalse(service.isMethodInheritedFrom(IC.class, IC.class.getMethod(methodName), asSet(IC.class)));

        assertTrue(service.isMethodInheritedFrom(AbstractC.class, AbstractC.class.getMethod(methodName), asSet(IC.class)));
        //not from self
        assertFalse(service.isMethodInheritedFrom(AbstractC.class, AbstractC.class.getMethod(methodName), asSet(AbstractC.class)));
        assertTrue(service.isMethodInheritedFrom(AbstractC.class, AbstractC.class.getMethod(methodName), asSet(IC.class, AbstractC.class)));


        assertTrue(service.isMethodInheritedFrom(ConcreteCFoo.class, ConcreteCFoo.class.getMethod(methodName), asSet(IC.class)));
        assertTrue(service.isMethodInheritedFrom(ConcreteCFoo.class, ConcreteCFoo.class.getMethod(methodName), asSet(AbstractC.class)));
        assertTrue(service.isMethodInheritedFrom(ConcreteCFoo.class, ConcreteCFoo.class.getMethod(methodName), asSet(IC.class, AbstractC.class)));

    }


    private interface NonChildWithGetSomeString {
        String getSomeString();
    }

    @SneakyThrows
    @Test
    void isMethodInheritedFrom_outsideHierarchy() {
        String methodName = "getSomeString";
        assertFalse(service.isMethodInheritedFrom(NonChildWithGetSomeString.class, NonChildWithGetSomeString.class.getMethod(methodName), asSet(IC.class)));
    }


    public static <T> Set<T> asSet(T... a) {
        return new HashSet<>(Arrays.asList(a));
    }
}