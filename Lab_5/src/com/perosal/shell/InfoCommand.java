package com.perosal.shell;

import com.perosal.Color;
import com.perosal.exceptions.InvalidCommandException;
import com.perosal.exceptions.InvalidPathException;
import com.perosal.media.Catalog;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.ContentHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class InfoCommand implements ShellCommand {
    @Override
    public Catalog run(String[] args, Catalog catalog) throws InvalidCommandException, InvalidPathException, IOException, ClassNotFoundException {
        if (args.length != 1) {
            throw new InvalidCommandException(Color.RED_BOLD + "[List]Invalid command" + Color.RESET);
        }

        catalog.getMediaItems().forEach(mediaItem -> {
            File file = mediaItem.getFile();
            System.out.println(Color.BLUE_BOLD + file.getName() + Color.RESET);
            showMetadata(file);
            System.out.println("");
        });

        return null;
    }

    private void showMetadata(File file) {
        ContentHandler handler = new BodyContentHandler();
        AutoDetectParser parser = new AutoDetectParser();
        Metadata metadata = new Metadata();
        ParseContext parseContext = new ParseContext();

        try (InputStream stream = new FileInputStream(file)) {
            parser.parse(stream, handler, metadata, parseContext);
            System.out.println("Document Metadata:");

            String[] metadataArray = metadata.names();

            for(String data : metadataArray) {
                System.out.println(data + ": " + metadata.get(data));
            }
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
