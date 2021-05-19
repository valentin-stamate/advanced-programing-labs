import loader.Loader;
import org.junit.jupiter.api.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

public class MainClass {

    public static void main(String... args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        String file = "source.SourceClass";
        String path = "C:\\Users\\Valentin\\Desktop\\Sem.ll\\Programare Avansata\\Laborator\\Laborator-Java\\lab_12\\build\\classes\\java\\main\\source";

        Loader loader = new Loader();

        Class<?> clazz = loader.loadClass(file);

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
