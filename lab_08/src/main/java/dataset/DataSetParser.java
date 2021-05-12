package dataset;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import database.dao.*;
import database.models.*;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class DataSetParser {

    private final static String DATASET_PATH = "C:\\Users\\Valentin\\Desktop\\Sem.ll\\Programare Avansata\\Laborator\\Laborator-Java\\Lab_8\\src\\main\\java\\dataset\\movies.csv";

    public static void putDataInDatabase() throws IOException, CsvValidationException {
        CSVReader csvReader = new CSVReader(new FileReader(DATASET_PATH));
        csvReader.readNext(); // skip the name column row

        String[] row;

        int i = 0;
        while ((row = csvReader.readNext()) != null) {

            Movie movie = rowToMovie(row);
            List<Actor> actors = rowToActorsList(row);
            List<Director> directors = rowToDirectorList(row);
            List<Genre> genres = rowToGenresList(row);

            addMoviesToDB(movie);
            addActorsToDB(actors);
            addDirectorsToDB(directors);
            addGenresToDB(genres);

            List<MovieActor> movieActors = getMovieActorsList(movie, actors);
            List<MovieDirector> movieDirectors = getMovieDirectorsList(movie, directors);
            List<MovieGenre> movieGenres = getMovieGenresList(movie, genres);

            addMovieActorsToDB(movieActors);
            addMovieDirectorsToDB(movieDirectors);
            addMovieGenresToDB(movieGenres);

            System.out.println(i++);
        }
    }

    private static void addMovieGenresToDB(List<MovieGenre> movieGenres) {
        MovieGenreDao movieGenreDao = new MovieGenreDao();
        for (MovieGenre movieGenre : movieGenres) {
            movieGenreDao.add(movieGenre);
        }
    }

    private static void addMovieDirectorsToDB(List<MovieDirector> movieDirectors) {
        MovieDirectorDao movieDirectorDao = new MovieDirectorDao();
        for (MovieDirector movieDirector : movieDirectors) {
            movieDirectorDao.add(movieDirector);
        }
    }

    private static void addMovieActorsToDB(List<MovieActor> movieActors) {
        MovieActorsDao movieActorsDao = new MovieActorsDao();
        for (MovieActor movieActor : movieActors) {
            movieActorsDao.add(movieActor);
        }
    }

    private static void addMoviesToDB(Movie movie) {
        MovieDao movieDao = new MovieDao();

        movieDao.addIfNotExists(movie);
        Movie dbMovie = movieDao.getMovieByMovieId(movie.getIdMovie());

        if (dbMovie == null) {
            System.out.println("Something went wrong");
            return;
        }

        movie.setId(dbMovie.getId());
    }

    private static void addGenresToDB(List<Genre> genres) {
        GenreDao genreDao = new GenreDao();
        for (Genre genre : genres) {
            genreDao.addIfNotExists(genre);
            Genre dbGenre = genreDao.getGenreByName(genre.getName());

            if (dbGenre == null) {
                System.out.println("Something went wrong");
                continue;
            }

            genre.setId(dbGenre.getId());

        }
    }

    private static void addDirectorsToDB(List<Director> directors) {
        DirectorsDao directorsDao = new DirectorsDao();
        for (Director director : directors) {
            directorsDao.addIfNotExists(director);
            Director dbDirector = directorsDao.getDirectorByName(director.getName());

            if (dbDirector == null) {
                System.out.println("Something went wrong");
                continue;
            }

            director.setId(dbDirector.getId());

        }
    }

    private static void addActorsToDB(List<Actor> actors) {
        ActorDao actorDao = new ActorDao();
        for (Actor actor : actors) {
            actorDao.addIfNotExists(actor);
            Actor dbActor = actorDao.getActorByName(actor.getName());
            if (dbActor == null) {
                System.out.println("Something went wrong");
                continue;
            }

            actor.setId(dbActor.getId());
        }
    }

    private static List<MovieActor> getMovieActorsList(Movie movie, List<Actor> actors) {
        List<MovieActor> movieActors = new ArrayList<>();

        for (Actor actor : actors) {
            movieActors.add(new MovieActor(movie.getIdMovie(), actor.getId()));
        }

        return movieActors;
    }

    private static List<MovieDirector> getMovieDirectorsList(Movie movie, List<Director> directors) {
        List<MovieDirector> movieDirectors = new ArrayList<>();

        for (Director director : directors) {
            movieDirectors.add(new MovieDirector(movie.getIdMovie(), director.getId()));
        }

        return movieDirectors;
    }

    private static List<MovieGenre> getMovieGenresList(Movie movie, List<Genre> genres) {
        List<MovieGenre> movieGenres = new ArrayList<>();

        for (Genre genre : genres) {
            movieGenres.add(new MovieGenre(movie.getIdMovie(), genre.getId()));
        }

        return movieGenres;
    }

    private static List<Genre> rowToGenresList(String[] row) {
        String[] genres = row[5].split(",");
        normalizeStringArray(genres);

        List<Genre> genreList = new ArrayList<>();
        for (String genre : genres) {
            genreList.add(new Genre(genre));
        }

        return genreList;
    }

    private static List<Director> rowToDirectorList(String[] row) {
        String[] directors = row[9].split(",");
        normalizeStringArray(directors);

        List<Director> directorList = new ArrayList<>();
        for (String director : directors) {
            directorList.add(new Director(director));
        }

        return directorList;
    }

    private static List<Actor> rowToActorsList(String[] row) {
        String[] actors = row[12].split(",");
        normalizeStringArray(actors);

        List<Actor> actorList = new ArrayList<>();
        for (String actor : actors) {
            actorList.add(new Actor(actor));
        }

        return actorList;
    }

    private static Movie rowToMovie(String[] row) {
        normalizeStringArray(row);
        return new Movie(row[0], row[1], row[4], row[6], Float.parseFloat(row[14]), Integer.parseInt(row[15]));
    }

    private static void normalizeStringArray(String[] row) {
        for (int i = 0; i < row.length; i++) {
            row[i] = normalizeString(row[i]);
        }
    }

    private static String normalizeString(String s) {
        return s.replaceAll("\\s+", " ").trim().replaceAll("'", "''");
    }

}
