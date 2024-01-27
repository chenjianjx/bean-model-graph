package org.beanmodelgraph.constructor.util;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CollectionTypeUtilsTest {


    public List getRawList() {
        return null;
    }

    public Object[] getRawArray() {
        return null;
    }

    public List<?> getVariableTypeList() {
        return null;
    }


    public List<String> getStringList() {
        return null;
    }

    public String[] getStringArray() {
        return null;
    }


    public List<String>[] getArrayOfStringLists() {
        return null;
    }

    public List<String[]> getListOfStringArrays() {
        return null;
    }

    public List<List> getListOfRawLists() {
        return null;
    }

    public Object[][] getArrayOfRawArrays() {
        return null;
    }

    public List<List<String>> getListOfStringLists() {
        return null;
    }

    public String[][] getArrayOfStringArrays() {
        return null;
    }

    @Test
    @SneakyThrows
    void getMethodGenericReturnTypeIfArrayOrCollection_rawList() {
        assertEquals(Object.class, CollectionTypeUtils.getMethodGenericReturnTypeIfArrayOrCollection(this.getClass().getMethod("getRawList")));
    }


    @Test
    @SneakyThrows
    void getMethodGenericReturnTypeIfArrayOrCollection_rawArray() {
        assertEquals(Object.class, CollectionTypeUtils.getMethodGenericReturnTypeIfArrayOrCollection(this.getClass().getMethod("getRawArray")));
    }


    @Test
    @SneakyThrows
    void getMethodGenericReturnTypeIfArrayOrCollection_variableTypeList() {
        assertThrows(UnsupportedOperationException.class, () -> CollectionTypeUtils.getMethodGenericReturnTypeIfArrayOrCollection(this.getClass().getMethod("getVariableTypeList")));
    }


    @Test
    @SneakyThrows
    void getMethodGenericReturnTypeIfArrayOrCollection_stringList() {
        assertEquals(String.class, CollectionTypeUtils.getMethodGenericReturnTypeIfArrayOrCollection(this.getClass().getMethod("getStringList")));
    }

    @Test
    @SneakyThrows
    void getMethodGenericReturnTypeIfArrayOrCollection_stringArray() {
        assertEquals(String.class, CollectionTypeUtils.getMethodGenericReturnTypeIfArrayOrCollection(this.getClass().getMethod("getStringArray")));
    }

    @Test
    @SneakyThrows
    void getMethodGenericReturnTypeIfArrayOrCollection_arrayOfStringLists() {
        assertEquals(List.class, CollectionTypeUtils.getMethodGenericReturnTypeIfArrayOrCollection(this.getClass().getMethod("getArrayOfStringLists")));
    }

    @Test
    @SneakyThrows
    void getMethodGenericReturnTypeIfArrayOrCollection_listOfStringArrays() {
        assertEquals(String[].class, CollectionTypeUtils.getMethodGenericReturnTypeIfArrayOrCollection(this.getClass().getMethod("getListOfStringArrays")));
    }

    @Test
    @SneakyThrows
    void getMethodGenericReturnTypeIfArrayOrCollection_listOfRawLists() {
        assertEquals(List.class, CollectionTypeUtils.getMethodGenericReturnTypeIfArrayOrCollection(this.getClass().getMethod("getListOfRawLists")));
    }

    @Test
    @SneakyThrows
    void getMethodGenericReturnTypeIfArrayOrCollection_listOfStringLists() {
        assertEquals(List.class, CollectionTypeUtils.getMethodGenericReturnTypeIfArrayOrCollection(this.getClass().getMethod("getListOfStringLists")));
    }

    @Test
    @SneakyThrows
    void getMethodGenericReturnTypeIfArrayOrCollection_arrayOfRawArrays() {
        assertEquals(Object[].class, CollectionTypeUtils.getMethodGenericReturnTypeIfArrayOrCollection(this.getClass().getMethod("getArrayOfRawArrays")));
    }

    @Test
    @SneakyThrows
    void getMethodGenericReturnTypeIfArrayOrCollection_arrayOfStringArrays() {
        assertEquals(String[].class, CollectionTypeUtils.getMethodGenericReturnTypeIfArrayOrCollection(this.getClass().getMethod("getArrayOfStringArrays")));
    }


    @Test
    void getMethodGenericReturnTypeIfArrayOrCollection_unsupportedReturnType() {
        Type fakeType = new Type() {
            @Override
            public String getTypeName() {
                return Type.super.getTypeName();
            }
        };

        assertThrows(UnsupportedOperationException.class, () -> CollectionTypeUtils.getMethodGenericReturnTypeIfArrayOrCollection(fakeType));
    }

}