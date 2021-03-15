package com.perosal.shell;

import com.perosal.Color;
import com.perosal.exceptions.InvalidCommandException;
import com.perosal.exceptions.InvalidPathException;
import com.perosal.media.Catalog;
import com.perosal.media.items.MediaItem;
import java.io.File;
import java.io.IOException;

public class PlayCommand implements ShellCommand{
    @Override
    public void run(String[] args, Catalog catalog) throws InvalidCommandException, InvalidPathException, IOException {
        if (args.length != 2) {
            throw new InvalidCommandException(Color.RED_BOLD + "[Play]" + Color.RESET);
        }

        String filePath = args[1];
        File file = new File(filePath);
        MediaItem mediaItem = Catalog.getMediaItemFromFile(file);

        Catalog.play(mediaItem);
    }
}
