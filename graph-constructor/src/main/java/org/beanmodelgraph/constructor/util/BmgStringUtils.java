package org.beanmodelgraph.constructor.util;

import java.util.Collection;

public class BmgStringUtils {
    public static String doubleQuote(String s) {
        return "\"" + s + "\"";
    }

    public static String join(Collection<String> strings, String delimiter) {
        if (strings == null || strings.isEmpty()) {
            return "";
        }

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
