package com.perosal.media;

import com.perosal.Color;
import com.perosal.media.items.MediaItem;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Catalog implements Serializable {
    private static final String fileSerializerName = "catalog.ser";
    private final List<MediaItem> mediaItems;

    public Catalog() {
        this.mediaItems = new ArrayList<>();
    }

    public Catalog(List<MediaItem> mediaItems) {
        this.mediaItems = mediaItems;
    }

    public void add(MediaItem item) {
        mediaItems.add(item);
    }

    public void list() {
        System.out.println(mediaItems);
    }

    public static void play(MediaItem mediaItem) throws IOException {
        Desktop.getDesktop().open(mediaItem.getFile());
    }

    public void save() throws IOException {
        serialize(this);
    }

    public static Catalog load() throws IOException, ClassNotFoundException {
        return (Catalog) deserialize();
    }

    private static void serialize(Object object) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(fileSerializerName);
        ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);

        outputStream.writeObject(object);

        outputStream.close();
        fileOutputStream.close();
    }

    private static Object deserialize() throws IOException, ClassNotFoundException {
        Object object;

        FileInputStream fileInputStream = new FileInputStream(fileSerializerName);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

        object = objectInputStream.readObject();

        return object;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(Color.YELLOW_BOLD + "The catalog contains the following files:\n\n" + Color.RESET);

        mediaItems.forEach(mediaItem -> {
            List<String> metadataList = mediaItem.getMetadata();

            for (String metadata : metadataList) {
                stringBuilder.append(metadata);
                stringBuilder.append("\n");
            }
            stringBuilder.append("\n");
        });

        return stringBuilder.toString();
    }
}
