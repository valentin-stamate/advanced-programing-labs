import loader.Loader;
import util.Util;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class MainClass {

    private final static String PATH = "C:\\Users\\Valentin\\Desktop\\Sem.ll\\Programare Avansata\\Laborator\\Laborator-Java\\lab_12\\build\\classes\\java\\test";
    private final static String PACKAGE_NAME = "source";

    public static void main(String... args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {

        Loader loader = new Loader();
        loader.addPath(PATH);

        File folder = new File(PATH + "\\" + PACKAGE_NAME);
        List<String> files = new ArrayList<>();
        Util.getFiles(folder, files, ".*([.class]|[.jar])");

        System.out.println("\nMethods with output for @Test annotated function: ");
        for (String file : files) {
            String rawFile = file.substring(0, file.lastIndexOf("."));
            loader.showClassInfo(PACKAGE_NAME + "." + rawFile);
        }

        System.out.println("\nPublic classes with @Test annotation");
        System.out.printf("| %15s | %20s | %20s | %20s |\n", "Method name", "Signature", "Return", "Execution Time");
        for (String file : files) {
            String rawFile = file.substring(0, file.lastIndexOf("."));
            loader.showClassInfoRequired(PACKAGE_NAME + "." + rawFile);
        }

        /* COMPILING JAVA FILES */

        System.out.println("\nCompiling Java Files\n");

        List<String> javaFiles = new ArrayList<>();
        String javaPath = "C:\\Users\\Valentin\\Desktop\\Sem.ll\\Programare Avansata\\Laborator\\Laborator-Java\\lab_12\\src\\main\\resources\\java_files";

        File javaFolder = new File(javaPath);

        Util.getFiles(javaFolder, javaFiles, ".*[.java]");

        for (String path : javaFiles) {
            String command = String.format("javac %s", "\"" + javaPath + "\\" +path + "\"");
            Util.runCommand(command);
        }

    }


}
