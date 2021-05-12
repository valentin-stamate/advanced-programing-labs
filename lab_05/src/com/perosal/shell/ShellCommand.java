package com.perosal.shell;

import com.perosal.exceptions.InvalidCommandException;
import com.perosal.exceptions.InvalidPathException;
import com.perosal.media.Catalog;

import java.io.IOException;

public interface ShellCommand {
    Catalog run(String[] args, Catalog catalog) throws InvalidCommandException, InvalidPathException, IOException, ClassNotFoundException;
}
