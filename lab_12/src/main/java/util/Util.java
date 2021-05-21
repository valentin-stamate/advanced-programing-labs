package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.regex.Pattern;

public class Util {

    /* TAKEN FROM Lab 10 */
    public static void getFiles(File folder, List<String> fileList, final String regex) {
        File[] files = folder.listFiles();

        if (files == null) {
            return;
        }

        for (File file : files) {
            if (file.isDirectory()) {
                getFiles(file, fileList, regex);
                continue;
            }

            if (Pattern.matches(regex, file.getPath())) {
                fileList.add(file.getName());
            }
        }
    }

    /* TAKEN FROM LAB 10 */
    public static void runCommand(String command) {
        try {
            Process process = Runtime.getRuntime().exec("cmd.exe /c " + command);
            printResults(process);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* TAKEN FROM LAB 10 */
    private static void printResults(Process process) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = "";
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
    }

}
