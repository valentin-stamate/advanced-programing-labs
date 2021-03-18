package com.perosal.media.items;

import com.perosal.exceptions.InvalidPathException;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class MediaItem implements Serializable {
    private final File file;
    private final List<String> fileMedata;
    private final List<String> fileFormat;

    protected MediaItem(File file, List<String> fileFormat) throws InvalidPathException {
        if (!file.getPath().contains("media")) {
            throw new InvalidPathException("The path is invalid!");
        }

        this.fileFormat = fileFormat;
        this.file = file;
        this.fileMedata = initializeMetadata();
    }

    public File getFile() {
        return file;
    }

    public List<String> getFileFormat() {
        return fileFormat;
    }

    public List<String> getMetadata() {
        return fileMedata;
    }

    public String getPath() {
        return file.getPath();
    }

    private List<String> initializeMetadata() {
        List<String> metadata = new ArrayList<>();

        metadata.add("Name: " + file.getName());
        metadata.add("Path: " + file.getPath());
        metadata.add("Size: " + file.length());
        metadata.add("Read: " + file.canRead());
        metadata.add("Write: " + file.canWrite());

        Date date = new Date();
        date.setTime(file.lastModified());
        metadata.add("Last Modified: " + date.toString());

        return metadata;
    }

    public boolean sameType(MediaItem mediaItemB) {
        return this.fileFormat == mediaItemB.getFileFormat();
    }
}
