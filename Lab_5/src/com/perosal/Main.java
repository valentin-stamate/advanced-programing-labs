package com.perosal;

import com.perosal.exceptions.InvalidPathException;
import com.perosal.media.Catalog;
import com.perosal.media.items.Image;
import com.perosal.media.items.MediaItem;
import com.perosal.media.items.OtherFile;
import com.perosal.media.items.Song;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final String mediaPath = "media";

    public static void main(String[] args) throws IOException, ClassNotFoundException, InvalidPathException {
        compulsory();
    }

    private static void compulsory() throws IOException, ClassNotFoundException, InvalidPathException {
        System.out.println(Color.GREEN_BOLD + "----==== Compulsory ====----" + Color.RESET);
        File mediaRoot = new File(mediaPath);

        List <MediaItem> mediaItems = new ArrayList<>();
        getFiles(mediaRoot, mediaItems);

        Catalog catalogA = new Catalog();
        mediaItems.forEach(catalogA::add);
        catalogA.save();

        Catalog catalogB = Catalog.load();

        Catalog.play(mediaItems.get(1));

        catalogB.list();
        System.out.println("");
        System.out.println(catalogB);
    }

    private static void getFiles(File folder, List<MediaItem> mediaList) throws InvalidPathException {
        File[] files = folder.listFiles();

        if (files == null) {
            return;
        }

        for (File file : files) {
            if (file.isDirectory()) {
                getFiles(file, mediaList);
                return;
            }

            MediaItem mediaItem = getMediaItemFromFile(file);
            mediaList.add(mediaItem);
        }
    }

    private static MediaItem getMediaItemFromFile(File file) throws InvalidPathException {
        String filePath = file.getPath();
        String extension = filePath.substring(filePath.lastIndexOf("."));

        if (Image.getSupportedTypes().contains(extension)) {
            return new Image(file);
        }

        if (Song.getSupportedTypes().contains(extension)) {
            return new Song(file);
        }

        return new OtherFile(file);
    }

}
