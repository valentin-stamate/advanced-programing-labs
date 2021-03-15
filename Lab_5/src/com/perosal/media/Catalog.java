package com.perosal.media;

import com.perosal.Color;
import com.perosal.exceptions.InvalidCommandException;
import com.perosal.exceptions.InvalidPathException;
import com.perosal.media.items.Image;
import com.perosal.media.items.MediaItem;
import com.perosal.media.items.OtherFile;
import com.perosal.media.items.Song;
import com.perosal.shell.*;

import javax.sound.midi.Soundbank;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

        stringBuilder.append(Color.YELLOW_BOLD).append("The catalog contains the following files:\n\n").append(Color.RESET);

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

    public static MediaItem getMediaItemFromFile(File file) throws InvalidPathException {
        String filePath = file.getPath();
        String extension = filePath.substring(filePath.lastIndexOf("."));

        if (com.perosal.media.items.Image.getSupportedTypes().contains(extension)) {
            return new Image(file);
        }

        if (Song.getSupportedTypes().contains(extension)) {
            return new Song(file);
        }

        return new OtherFile(file);
    }

    public Catalog shell() throws InvalidCommandException, IOException, InvalidPathException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        Catalog catalogLoaded = null;
        String commandMessage = "";

        while (true) {
            String command = "";
            command = scanner.nextLine().trim();

            String[] args = command.split("\\s+");

            if (args.length == 0) {
                throw new InvalidCommandException(Color.RED_BOLD + "[Read Line]Invalid command" + Color.RESET);
            }

            if (command.equals("exit")) {
                break;
            }

            ShellCommand shellCommand = null;
            if (args[0].equals("add")) {
                shellCommand = new AddCommand();
                commandMessage = "MediaItem successfully added";
            } else if (args[0].equals("list")) {
                shellCommand = new ListCommand();
                commandMessage = "List successfully displayed";
            } else if (args[0].equals("play")) {
                shellCommand = new PlayCommand();
                commandMessage = "Media successfully played";
            } else if (args[0].equals("save")) {
                shellCommand = new SaveCommand();
                commandMessage = "This catalog was saved";
            } else if (!args[0].equals("load")) {
                throw new InvalidCommandException(Color.RED_BOLD + "Invalid command" + Color.RESET);
            }

            if (shellCommand != null) {
                shellCommand.run(args, this);
            }

            if (args[0].equals("load")) {
                catalogLoaded = (new LoadCommand()).run(args);
                commandMessage = "The catalog was fetched";
            }

            System.out.println(Color.YELLOW_BOLD + commandMessage + Color.RESET);
            System.out.println("");
        }

        return catalogLoaded;
    }
}
