package com.perosal.media.items;

import com.perosal.exceptions.InvalidPathException;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Image extends MediaItem {
    private static final String[] ARRAY_EXTENSION = {".img", ".png"};
    private static final List<String> SUPPORTED_FORMATS = new ArrayList<>(Arrays.asList(ARRAY_EXTENSION));

    public Image(File file) throws InvalidPathException {
        super(file, SUPPORTED_FORMATS);
    }

    public static List<String> getSupportedTypes() {
        return SUPPORTED_FORMATS;
    }

    @Override
    public String toString() {
        return "Image{path='" + getPath() + "}";
    }

    public static List<String> getSupportedFormats() {
        return SUPPORTED_FORMATS;
    }

    public static Image getDummy() throws InvalidPathException {
        return new Image(new File("media/hello_world.png"));
    }

}
