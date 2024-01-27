package org.beanmodelgraph.constructor.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CollectionTypeUtils {

    public static boolean isClassArrayOrCollection(Class<?> clazz) {
        return clazz.isArray() || Collection.class.isAssignableFrom(clazz);
    }

    public static boolean isMap(Class<?> clazz){
        return Map.class.isAssignableFrom(clazz);
    }

    public static Class<?> getMethodGenericReturnTypeIfArrayOrCollection(Method method) {
        Type genericReturnType = method.getGenericReturnType();
        return getMethodGenericReturnTypeIfArrayOrCollection(genericReturnType);
    }

    static Class<?> getMethodGenericReturnTypeIfArrayOrCollection(Type methodGenericReturnType) {
        if (methodGenericReturnType instanceof Class) { //raw collection, raw array, non-raw array
            Optional<Class<?>> componentType = Optional.ofNullable(((Class<?>) methodGenericReturnType).getComponentType());
            return componentType.isPresent() ? componentType.get() : Object.class;
        } else if (methodGenericReturnType instanceof GenericArrayType) {//array of non-raw collection
            Type componentType = ((GenericArrayType) methodGenericReturnType).getGenericComponentType();
            return convertToRawType(componentType);
        } else if (methodGenericReturnType instanceof ParameterizedType) { //non-raw collection
            Type[] typeArguments = ((ParameterizedType) methodGenericReturnType).getActualTypeArguments();
            return convertToRawType(typeArguments[0]);
        } else {
            throw new UnsupportedOperationException(methodGenericReturnType.getClass().getName());
        }
    }

    private static Class<?> convertToRawType(Type type) {
        if (type instanceof Class) {
            return (Class<?>) type;
        }  else if (type instanceof ParameterizedType) {
            return (Class<?>) ((ParameterizedType) type).getRawType();
        } else {
            throw new UnsupportedOperationException(type.getClass().getName());
        }
    }
}
