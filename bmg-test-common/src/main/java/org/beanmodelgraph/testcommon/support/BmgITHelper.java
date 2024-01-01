package org.beanmodelgraph.testcommon.support;

import java.io.File;

public class BmgITHelper {

    public static File createSpreadsheetFile(String prefix, String fileExtension) {
        File dir = new File(System.getProperty("java.io.tmpdir"), "/bmg-it-test");
        dir.mkdirs();
        String filename = prefix + "-" + System.nanoTime() + "." + fileExtension;
        File file = new File(dir, filename);
        System.out.println("File created: " + file.getAbsolutePath());
        return file;
    }
}
