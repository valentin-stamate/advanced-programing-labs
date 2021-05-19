package loader;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class Loader extends URLClassLoader {
    public Loader() {
        super(new URL[0], ClassLoader.getSystemClassLoader());
    }

    public void addPath(String path) {
        File file = new File(path);
        if (file.exists()) {
            URL url = null;

            try {
                url = file.toURI().toURL();
            } catch (MalformedURLException e) {
                System.out.println("Error adding file");
            }

            addURL(url);
        }
    }

    @Override
    public void addURL(URL url) {
        super.addURL(url);
    }

    public void showClassInfo(final String fileName) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        Class<?> clazz = loadClass(fileName);

        System.out.println("Package: ");
        System.out.println(clazz.getPackage());

        Method[] methods = clazz.getMethods();

        System.out.println("\nMethods with output for @Test annotated function: ");
        for (Method method : methods) {
            System.out.printf("%10s -> ", method.getName());

            Class<?>[] parameters = method.getParameterTypes();

            StringBuilder stringBuilder = new StringBuilder();

            for (Class<?> parameter : parameters) {
                stringBuilder.append(parameter.getName()).append(" ");
            }
            System.out.printf("| %20s |", stringBuilder);

            Annotation[] annotations = method.getAnnotations();
            stringBuilder = new StringBuilder();

            String methodOutput = "";
            for (Annotation annotation : annotations) {
                methodOutput = "";
                stringBuilder.append(annotation.toString()).append(" ");

                if (annotation.toString().equals("@org.junit.jupiter.api.Test()") && Modifier.isStatic(method.getModifiers())) {
                    Object obj = clazz.getConstructor().newInstance();
                    methodOutput = (String) method.invoke(obj);
                }

            }

            System.out.printf("| %45s |", stringBuilder);
            System.out.printf("| %20s |", methodOutput);

            System.out.println("");
        }
    }
}
