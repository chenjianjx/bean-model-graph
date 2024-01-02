package org.beanmodelgraph.constructor.util;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Optional;

public class CollectionTypeUtils {

    public static boolean isClassArrayOrCollection(Class<?> clazz) {
        return clazz.isArray() || Collection.class.isAssignableFrom(clazz);
    }

    public static Class<?> getMethodGenericReturnTypeIfArrayOrCollection(Method method) {
        Type genericReturnType = method.getGenericReturnType();
        if (genericReturnType instanceof Class) {
            Optional<Class<?>> componentType = Optional.ofNullable(((Class<?>) genericReturnType).getComponentType());
            if (componentType.isPresent()) {
                return componentType.get();
            } else {
                return Object.class;
            }
        } else if (genericReturnType instanceof GenericArrayType) {
            Type componentType = ((GenericArrayType) genericReturnType).getGenericComponentType();
            return (Class<?>) ((ParameterizedType) componentType).getRawType();
        } else if (genericReturnType instanceof ParameterizedType) {
            Type[] typeArguments = ((ParameterizedType) genericReturnType).getActualTypeArguments();
            Type type = typeArguments[0];
            return (Class<?>) type;
        } else {
            throw new UnsupportedOperationException();
        }
    }

}
