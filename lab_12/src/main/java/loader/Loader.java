package loader;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class Loader extends URLClassLoader {
    public Loader() {
        super(new URL[0], ClassLoader.getSystemClassLoader());
    }

    public void addFile(String path) {
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
}
