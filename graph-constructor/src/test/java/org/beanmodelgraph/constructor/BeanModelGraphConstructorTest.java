package org.beanmodelgraph.constructor;

import lombok.Value;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BeanModelGraphConstructorTest {

    @Value
    public static class DedupeTestA {
        private DedupeTestB b1;
        private DedupeTestB b2;
    }

    public static class DedupeTestB {
        private DedupeTestA a;
    }

    @Test
    void constructFromBeanClass_dedupeTest() {
        BeanModelGraphConstructor constructor = new BeanModelGraphConstructor(DedupeTestA.class);
        constructor.construct();
        assertEquals(2, constructor.existingNodes.size());
    }
}