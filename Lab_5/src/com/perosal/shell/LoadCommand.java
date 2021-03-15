package com.perosal.shell;

import com.perosal.Color;
import com.perosal.exceptions.InvalidCommandException;
import com.perosal.media.Catalog;
import java.io.IOException;

public class LoadCommand implements ShellCommand {
    private Catalog loadedCatalog;

    @Deprecated
    @Override
    public void run(String[] args, Catalog catalog) throws IOException, InvalidCommandException, ClassNotFoundException {
        loadedCatalog = run(args);
    }

    @Deprecated
    public Catalog getCatalog(String[] args, Catalog catalog) throws ClassNotFoundException, IOException, InvalidCommandException {
        run(args, catalog);
        return loadedCatalog;
    }

    public Catalog run(String[] args) throws InvalidCommandException, IOException, ClassNotFoundException {
        if (args.length != 1) {
            throw new InvalidCommandException(Color.RED_BOLD + "[Load]Invalid command" + Color.RESET);
        }

        return Catalog.load();
    }
}
