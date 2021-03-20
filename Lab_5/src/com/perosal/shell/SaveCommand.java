package com.perosal.shell;

import com.perosal.Color;
import com.perosal.exceptions.InvalidCommandException;
import com.perosal.media.Catalog;
import java.io.IOException;

public class SaveCommand implements ShellCommand{
    @Override
    public Catalog run(String[] args, Catalog catalog) throws InvalidCommandException, IOException {
        if (args.length != 1) {
            throw new InvalidCommandException(Color.RED_BOLD + "[Save]Invalid command" + Color.RESET);
        }

        catalog.save();

        return null;
    }
}
