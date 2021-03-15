package com.perosal.media.items;

import com.perosal.exceptions.InvalidPathException;
import java.io.File;

public class OtherFile extends MediaItem{

    public OtherFile(File file) throws InvalidPathException {
        super(file);
    }

    @Override
    public String toString() {
        return "OtherFile{path='" + getPath() + "}";
    }
}
