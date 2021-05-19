package util;

import java.io.File;
import java.util.List;

public class Util {

    public static void getFiles(File folder, List<String> fileList) {
        File[] files = folder.listFiles();

        if (files == null) {
            return;
        }

        for (File file : files) {
            if (file.isDirectory()) {
                getFiles(file, fileList);
                continue;
            }

            if (file.getPath().endsWith(".class") || file.getPath().endsWith(".jar")) {
                fileList.add(file.getPath());
            }
        }
    }

}
