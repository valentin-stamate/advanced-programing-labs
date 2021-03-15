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
        super(file);
    }

    public static List<String> getSupportedTypes() {
        return supportedFormats;
    }

    @Override
    public String toString() {
        return "Image{path='" + getPath() + "}";
    }
}
