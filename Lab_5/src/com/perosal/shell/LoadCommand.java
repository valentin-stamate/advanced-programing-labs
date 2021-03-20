package com.perosal.shell;

import com.perosal.Color;
import com.perosal.exceptions.InvalidCommandException;
import com.perosal.media.Catalog;
import java.io.IOException;

public class LoadCommand implements ShellCommand {

    public Catalog run(String[] args, Catalog catalog) throws InvalidCommandException, IOException, ClassNotFoundException {
        catalog = null;
        if (args.length != 1) {
            throw new InvalidCommandException(Color.RED_BOLD + "[Load]Invalid command" + Color.RESET);
        }

        return Catalog.load();
    }
}
