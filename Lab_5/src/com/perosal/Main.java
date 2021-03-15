package com.perosal;

import com.perosal.exceptions.InvalidCommandException;
import com.perosal.exceptions.InvalidPathException;
import com.perosal.media.Catalog;
import com.perosal.media.items.MediaItem;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final String mediaPath = "media";

    public static void main(String[] args) throws IOException, ClassNotFoundException, InvalidPathException, InvalidCommandException {
        compulsory(); // + compulsory
    }

    private static void compulsory() throws IOException, ClassNotFoundException, InvalidPathException, InvalidCommandException {
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

        System.out.println(Color.YELLOW_BOLD + "----==== Compulsory ====----" + Color.RESET);
        Catalog catalogC = catalogB.shell();

        if (catalogC != null) {
            System.out.println(catalogC);
        }
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

            MediaItem mediaItem = Catalog.getMediaItemFromFile(file);
            mediaList.add(mediaItem);
        }
    }

}
