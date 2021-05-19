import loader.Loader;
import util.Util;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class MainClass {

    private final static String PATH = "C:\\Users\\Valentin\\Desktop";
    private final static String PACKAGE_NAME = "source";

    public static void main(String... args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {

        Loader loader = new Loader();
        loader.addPath(PATH);

        File folder = new File(PATH + "\\" + PACKAGE_NAME);
        List<String> files = new ArrayList<>();
        Util.getFiles(folder, files);

        for (String file : files) {
            String rawFile = file.substring(0, file.lastIndexOf("."));
            loader.showClassInfo(PACKAGE_NAME + "." + rawFile);
        }

    }


}
