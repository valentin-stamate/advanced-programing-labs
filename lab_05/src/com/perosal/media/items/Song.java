package com.perosal.media.items;

import com.perosal.exceptions.InvalidPathException;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Song extends MediaItem {
    private static final String[] ARRAY_EXTENSION = {".m4a", ".mp4", ".mkv", ".avi"};
    private static final List<String> SUPPORTED_FORMATS = new ArrayList<>(Arrays.asList(ARRAY_EXTENSION));

    public Song(File file) throws InvalidPathException {
        super(file, SUPPORTED_FORMATS);
    }

    public static List<String> getSupportedTypes() {
        return SUPPORTED_FORMATS;
    }

    @Override
    public String toString() {
        return "Song{path='" + getPath() + "}";
    }

    public List<String> getSupportedFormats() {
        return SUPPORTED_FORMATS;
    }

    public static Song getDummy() throws InvalidPathException {
        return new Song(new File("media/song.m4a"));
    }
}
