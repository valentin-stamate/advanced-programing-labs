package database.dataset_statistics;

import database.dao.ActorDao;
import database.dao.GenreDao;
import database.dao.MovieDao;
import database.dao.instances.ActorActivity;
import database.models.Genre;
import database.models.Movie;
import freemarker.template.*;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatasetStatistics {

    private final static String TEMPLATE_FOLDER_PATH = "templates/";
    private final static String TEMPLATE_IN = "movie_statistics.ftl";
    private final static String TEMPLATE_OUT = "movie_statistics.html";

    Map<String, Object> data;

    public DatasetStatistics() {
        data = new HashMap<>();
    }

    public void init() {
        MovieDao movieDao = new MovieDao();
        ActorDao actorDao = new ActorDao();
        GenreDao genreDao = new GenreDao();

        List<Movie> bestMovies = movieDao.getTopMovies(false);
        List<Movie> worstMovies = movieDao.getTopMovies(true);
        List<ActorActivity> activeActors = actorDao.getTopActiveActors();
        List<Genre> topGenres = genreDao.getTopGenres();

        data.put("bestMovies", bestMovies);
        data.put("worstMovies", worstMovies);
        data.put("activeActors", activeActors);
        data.put("topGenres", topGenres);

    }

    public void generateStatistics() {
        Version version = Configuration.VERSION_2_3_28;
        File file = new File(TEMPLATE_FOLDER_PATH);

        Configuration configuration = new Configuration(version);

        try {
            configuration.setDirectoryForTemplateLoading(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        configuration.setDefaultEncoding("UTF-8");
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        Template template = null;

        try {
            template = configuration.getTemplate(TEMPLATE_IN);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Writer fileWriter;

        try {
            fileWriter = new FileWriter(TEMPLATE_OUT);
            template.process(data, fileWriter);
            fileWriter.close();
        } catch (TemplateException | IOException e) {
            e.printStackTrace();
        }
    }

}
