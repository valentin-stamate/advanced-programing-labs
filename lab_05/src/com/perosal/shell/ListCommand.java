package com.perosal.shell;

import com.perosal.Color;
import com.perosal.exceptions.InvalidCommandException;
import com.perosal.media.Catalog;

public class ListCommand implements ShellCommand {
    @Override
    public Catalog run(String[] args, Catalog catalog) throws InvalidCommandException {
        if (args.length != 1) {
            throw new InvalidCommandException(Color.RED_BOLD + "[List]Invalid command" + Color.RESET);
        }

        catalog.list();

        return null;
    }
}
