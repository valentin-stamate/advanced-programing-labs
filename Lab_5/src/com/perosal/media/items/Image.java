package com.perosal.media.items;

import com.perosal.exceptions.InvalidPathException;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Image extends MediaItem {
    private static final String[] arrayExtension = {".img", ".png"};
    private static final List<String> supportedFormats = new ArrayList<>(Arrays.asList(arrayExtension));

    public Image(File file) throws InvalidPathException {
        super(file, supportedFormats);
    }

    public static List<String> getSupportedTypes() {
        return supportedFormats;
    }

    @Override
    public String toString() {
        return "Image{path='" + getPath() + "}";
    }

    public static List<String> getSupportedFormats() {
        return supportedFormats;
    }

    public static Image getDummy() throws InvalidPathException {
        return new Image(new File("media/hello_world.png"));
    }

}
