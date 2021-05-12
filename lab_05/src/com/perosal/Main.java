package com.perosal;

import com.perosal.exceptions.InvalidCommandException;
import com.perosal.exceptions.InvalidPathException;
import com.perosal.media.Catalog;
import com.perosal.media.items.Image;
import com.perosal.media.items.MediaItem;
import com.perosal.media.items.OtherFile;
import com.perosal.media.items.Song;
import java.io.*;
import java.util.*;

public class Main {

    private static final String mediaPath = "media";

    public static void main(String[] args) {
        compulsory(); // + optional + fist bonus point
        bonus();

    }

    private static void bonus() {
        System.out.println(Color.BLUE_BOLD + "----==== Bonus ====----" + Color.RESET);

        System.out.println("");

        int graphLength = 33;

        List<MediaItem> mediaItemsRow = new ArrayList<>(graphLength);

        for (int i = 1; i <= 11; i++) {
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

        System.out.println(Color.YELLOW_BOLD + "----==== Optional ====----" + Color.RESET);
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
