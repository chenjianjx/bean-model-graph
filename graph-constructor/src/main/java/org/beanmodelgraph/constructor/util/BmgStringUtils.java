package org.beanmodelgraph.constructor.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Collection;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BmgStringUtils {
    public static String doubleQuote(String s) {
        return "\"" + s + "\"";
    }

    public static String join(@NonNull Collection<String> strings, String delimiter) {
        StringBuilder sb = new StringBuilder();
        boolean first = true;

        for (String str : strings) {
            if (!first) {
                sb.append(delimiter);
            }
            sb.append(str);
            first = false;
        }

        return sb.toString();
    }
}
