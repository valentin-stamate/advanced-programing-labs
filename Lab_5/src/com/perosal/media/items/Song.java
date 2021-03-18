package com.perosal.media.items;

import com.perosal.exceptions.InvalidPathException;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Song extends MediaItem {
    private static final String[] arrayExtension = {".m4a", ".mp4", ".mkv", ".avi"};
    private static final List<String> supportedFormats = new ArrayList<>(Arrays.asList(arrayExtension));

    public Song(File file) throws InvalidPathException {
        super(file, supportedFormats);
    }

    public static List<String> getSupportedTypes() {
        return supportedFormats;
    }

    @Override
    public String toString() {
        return "Song{path='" + getPath() + "}";
    }

    public List<String> getSupportedFormats() {
        return supportedFormats;
    }

    public static Song getDummy() throws InvalidPathException {
        return new Song(new File("media/song.m4a"));
    }
}
