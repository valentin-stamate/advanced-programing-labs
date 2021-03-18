package com.perosal.media.items;

import com.perosal.exceptions.InvalidPathException;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OtherFile extends MediaItem{
    private static final String[] arrayExtension = {".pdf", ".txt", ".zip", ".rar", ".7zip"};
    private static final List<String> supportedFormats = new ArrayList<>(Arrays.asList(arrayExtension));

    public OtherFile(File file) throws InvalidPathException {
        super(file, supportedFormats);
    }

    @Override
    public String toString() {
        return "OtherFile{path='" + getPath() + "}";
    }

    public static List<String> getSupportedFormats() {
        return supportedFormats;
    }

    public static OtherFile getDummy() throws InvalidPathException {
        return new OtherFile(new File("media/document.txt"));
    }
}
