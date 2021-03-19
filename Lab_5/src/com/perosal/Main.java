package com.perosal;

import com.perosal.exceptions.InvalidCommandException;
import com.perosal.exceptions.InvalidPathException;
import com.perosal.media.Catalog;
import com.perosal.media.items.Image;
import com.perosal.media.items.MediaItem;
import com.perosal.media.items.OtherFile;
import com.perosal.media.items.Song;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.txt.TXTParser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class Main {

    private static final String mediaPath = "media";

    public static void main(String[] args) {
        compulsory(); // + optional
        bonus();
    }

    private static void bonus() {
        System.out.println(Color.BLUE_BOLD + "----==== Bonus ====----" + Color.RESET);

        BodyContentHandler handler   = new BodyContentHandler();
        TXTParser parser             = new TXTParser();
        Metadata metadata            = new Metadata();
        ParseContext pcontext        = new ParseContext();

        try (InputStream stream = AutoDetectParseExample.class.getResourceAsStream("catalog.ser")) {
            parser.parse(stream, handler, metadata, pcontext);
            System.out.println("Document Content:" + handler.toString());
            System.out.println("Document Metadata:");
            String[] metadatas = metadata.names();
            for(String data : metadatas) {
                System.out.println(data + ":   " + metadata.get(data));
            }
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }

        File file = new File("media");

        int graphLength = 75;

        List<MediaItem> mediaItemsRow = new ArrayList<>(graphLength);

        for (int i = 1; i <= 25; i++) {
            try {
                mediaItemsRow.add(Image.getDummy());
                mediaItemsRow.add(OtherFile.getDummy());
                mediaItemsRow.add(Song.getDummy());
            } catch (InvalidPathException e) {
                e.printStackTrace();
            }
        }

        List<MediaItem> mediaItemsCol = new ArrayList<>(mediaItemsRow);

        boolean[][] graph = new boolean[graphLength][graphLength];

        for (int i = 0; i < graphLength; i++) {
            for (int j = i + 1; j < graphLength; j++) {
                MediaItem mediaItemA = mediaItemsRow.get(i);
                MediaItem mediaItemB = mediaItemsCol.get(j);

                if (mediaItemA.sameType(mediaItemB)) {
                    graph[i][j] = true;
                    graph[j][i] = true;
                }
            }
        }
        
        boolean[] visited = new boolean[graphLength];

        List<Queue<Integer>> splitMedia = new ArrayList<>();

        for (int i = 0; i < graphLength; i++) {
            if (!visited[i]) {
                Queue<Integer> similarMediaItem = new LinkedList<>();
                dfs(i, graph, visited, similarMediaItem);
                splitMedia.add(similarMediaItem);
            }
        }

        int dayNumber = 1;

        while (true) {
            List<MediaItem> currentDayFiles = new ArrayList<>();

            int filesFound = 0;
            for (Queue<Integer> files : splitMedia) {
                if (!files.isEmpty()) {
                    MediaItem mediaItem = mediaItemsCol.get(files.poll());
                    currentDayFiles.add(mediaItem);
                    filesFound++;
                }
            }

            if (filesFound == 0) {
                break;
            }

            System.out.println("Day" + dayNumber++);
            currentDayFiles.forEach(System.out::println);
            System.out.println("");
        }

    }

    public static void dfs(int node, boolean[][] graph, boolean[] visited, Queue<Integer> similarMediaItem) {
        if (visited[node]) {
            return;
        }

        similarMediaItem.add(node);
        visited[node] = true;

        for (int j = 0; j < graph.length; j++) {
            if (graph[node][j] && !visited[j]) {
                dfs(j, graph, visited, similarMediaItem);
            }
        }
    }

    private static void compulsory() {
        System.out.println(Color.GREEN_BOLD + "----==== Compulsory ====----" + Color.RESET);
        File mediaRoot = new File(mediaPath);

        List <MediaItem> mediaItems = new ArrayList<>();
        getFiles(mediaRoot, mediaItems);

        Catalog catalogA = new Catalog();
        mediaItems.forEach(catalogA::add);

        try {
            catalogA.save();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Catalog catalogB = null;
        try {
            catalogB = Catalog.load();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            Catalog.play(mediaItems.get(1));
        } catch (IOException e) {
            e.printStackTrace();
        }

        catalogB.list();
        System.out.println("");
        System.out.println(catalogB);

        System.out.println(Color.YELLOW_BOLD + "----==== Compulsory ====----" + Color.RESET);
        Catalog catalogC = null;

        try {
            catalogC = catalogB.shell();
        } catch (InvalidCommandException | IOException | InvalidPathException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        if (catalogC != null) {
            System.out.println(catalogC);
        }
    }

    private static void showGraph(boolean[][] graph) {
        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph.length; j++) {
                System.out.print((graph[i][j] ? 1 : 0) + " ");
            }
            System.out.println("");
        }
    }

    private static void getFiles(File folder, List<MediaItem> mediaList) {
        File[] files = folder.listFiles();

        if (files == null) {
            return;
        }

        for (File file : files) {
            if (file.isDirectory()) {
                getFiles(file, mediaList);
                return;
            }

            MediaItem mediaItem = null;
            try {
                mediaItem = Catalog.getMediaItemFromFile(file);
            } catch (InvalidPathException e) {
                e.printStackTrace();
            }
            mediaList.add(mediaItem);
        }
    }

}

class AutoDetectParseExample {
    public static void main(String[] args) {
        try {
            System.out.println(parseToPlainText());
        } catch (IOException | SAXException | TikaException e) {
            e.printStackTrace();
        }
    }
    public static String parseToPlainText() throws IOException, SAXException, TikaException {
        BodyContentHandler handler = new BodyContentHandler();

        AutoDetectParser parser = new AutoDetectParser();
        Metadata metadata = new Metadata();
        try (InputStream stream = AutoDetectParseExample.class.getResourceAsStream("Hello.txt")) {
            parser.parse(stream, handler, metadata);
            return handler.toString();
        }
    }
}
