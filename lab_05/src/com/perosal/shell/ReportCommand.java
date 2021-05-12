package com.perosal.shell;

import com.perosal.Color;
import com.perosal.exceptions.InvalidCommandException;
import com.perosal.exceptions.InvalidPathException;
import com.perosal.media.Catalog;
import freemarker.template.*;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ReportCommand implements ShellCommand{

    @Override
    public Catalog run(String[] args, Catalog catalog) throws InvalidCommandException, InvalidPathException, IOException, ClassNotFoundException {
        generateCatalogTemplate(catalog);
        return null;
    }

    private static void generateCatalogTemplate(Catalog catalog) {
        System.out.println("\n" + Color.GREEN_BOLD + "Generating catalog document" + Color.RESET);

        if (catalog == null) {
            System.out.println("The catalog is null");
            return;
        }

        Version version = Configuration.VERSION_2_3_28;
        File file = new File("templates/");

        Configuration cfg = new Configuration(version);

        try {
            cfg.setDirectoryForTemplateLoading(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        Map<String, Catalog> data = new HashMap<>();
        data.put("catalog", catalog);

        Template template = null;

        try {
            template = cfg.getTemplate("catalog_model.ftl");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Writer consoleWriter = new OutputStreamWriter(System.out);

        try {
            template.process(data, consoleWriter);
        } catch (TemplateException | IOException e) {
            e.printStackTrace();
        }

        Writer fileWriter = null;

        try {
            fileWriter = new FileWriter("catalog_information.html");
            template.process(data, fileWriter);
            fileWriter.close();
        } catch (TemplateException | IOException e) {
            e.printStackTrace();
        }

    }
}
