package org.beanmodelgraph.constructor.util;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public class CollectionTypeUtils {

    public static boolean isClassArrayOrCollection(Class<?> clazz) {
        return clazz.isArray() || Collection.class.isAssignableFrom(clazz);
    }

    public static boolean isMap(Class<?> clazz){
        return Map.class.isAssignableFrom(clazz);
    }

    public static Class<?> getMethodGenericReturnTypeIfArrayOrCollection(Method method) {
        Type genericReturnType = method.getGenericReturnType();
        if (genericReturnType instanceof Class) { //raw collection, raw array, non-raw array
            Optional<Class<?>> componentType = Optional.ofNullable(((Class<?>) genericReturnType).getComponentType());
            return componentType.isPresent() ? componentType.get() : Object.class;
        } else if (genericReturnType instanceof GenericArrayType) {//array of non-raw collection
            Type componentType = ((GenericArrayType) genericReturnType).getGenericComponentType();
            return convertToRawType(componentType);
        } else if (genericReturnType instanceof ParameterizedType) { //non-raw collection
            Type[] typeArguments = ((ParameterizedType) genericReturnType).getActualTypeArguments();
            return convertToRawType(typeArguments[0]);
        } else {
            throw new UnsupportedOperationException(genericReturnType.getClass().getName());
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
