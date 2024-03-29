package org.beanmodelgraph.constructor.helper;

import lombok.NonNull;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * one time use
 */
public class AtomicTypeResolver {

    private static Set<Class<?>> defaultAtomicTypes = new HashSet<>();
    private final Set<Class<?>> allAtomicTypes = new HashSet<>();

    public AtomicTypeResolver(@NonNull Set<Class<?>> additionalAtomicTypes) {
        this.allAtomicTypes.addAll(defaultAtomicTypes);
        this.allAtomicTypes.addAll(additionalAtomicTypes);
    }

    static {
        defaultAtomicTypes.add(boolean.class);
        defaultAtomicTypes.add(short.class);
        defaultAtomicTypes.add(int.class);
        defaultAtomicTypes.add(long.class);
        defaultAtomicTypes.add(float.class);
        defaultAtomicTypes.add(double.class);

        defaultAtomicTypes.add(Boolean.class);
        defaultAtomicTypes.add(Short.class);
        defaultAtomicTypes.add(Integer.class);
        defaultAtomicTypes.add(Long.class);
        defaultAtomicTypes.add(Float.class);
        defaultAtomicTypes.add(Double.class);

        defaultAtomicTypes.add(BigInteger.class);
        defaultAtomicTypes.add(BigDecimal.class);


        defaultAtomicTypes.add(Date.class);
        defaultAtomicTypes.add(LocalDate.class);
        defaultAtomicTypes.add(LocalDateTime.class);

        defaultAtomicTypes.add(String.class);
        defaultAtomicTypes.add(CharSequence.class);

        defaultAtomicTypes.add(Enum.class);

        defaultAtomicTypes.add(Object.class);
        defaultAtomicTypes.add(Class.class);
    }

    public boolean isAtomicType(Class<?> clazz) {
        return allAtomicTypes.contains(clazz);
    }
}
