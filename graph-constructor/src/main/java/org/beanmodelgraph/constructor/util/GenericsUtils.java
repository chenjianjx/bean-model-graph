package org.beanmodelgraph.constructor.util;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * <p>Get a variable's java type, or a collection's generic type </p>
 *
 * @author chenjianjx (Basically copied from Internet)
 */
public class GenericsUtils {


    public static Class<?> getMethodGenericReturnType(Method method) {
        return chooseOneType(method.getReturnType(), method.getGenericReturnType());
    }


    static Class<?> chooseOneType(Class<?> literalType, Type genericType) {
        if (genericType instanceof ParameterizedType) {
            return digFromGenericType(genericType);
        }
        return literalType;
    }

    static Class<?> digFromGenericType(Type genericType) {
        Type[] typeArguments = ((ParameterizedType) genericType).getActualTypeArguments();
        Type type = typeArguments[0];
        if (type instanceof ParameterizedType) {
            return digFromGenericType(type);
        }

        if (type instanceof Class) {
            return (Class<?>) type;
        }

        return Object.class;

    }
}
