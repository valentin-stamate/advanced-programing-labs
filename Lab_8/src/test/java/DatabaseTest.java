import database.dao.GenreDao;
import database.dao.MovieDao;
import database.dao.MovieGenreDao;
import database.models.Genre;
import database.models.Movie;
import database.models.MovieGenre;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.Date;

public class DatabaseTest {

    @Test
    public void insertMovie() {
        MovieDao movieDao = new MovieDao();
        GenreDao genreDao = new GenreDao();
        MovieGenreDao movieGenreDao = new MovieGenreDao();

        Movie movie = new Movie("Avatar", new Timestamp(new Date().getTime()), "1h", 10);
        movieDao.add(movie);

        Genre genreA = new Genre("drama");
        Genre genreB = new Genre("action");
        genreDao.add(genreA);
        genreDao.add(genreB);

        Movie desiredMovie = movieDao.getMovieByTitle("Avatar");
        genreA = genreDao.getGenreByName("drama");
        genreB = genreDao.getGenreByName("action");

        MovieGenre movieGenreA = new MovieGenre(desiredMovie.getId(), genreA.getId());
        MovieGenre movieGenreB = new MovieGenre(desiredMovie.getId(), genreB.getId());

        movieGenreDao.add(movieGenreA);
        movieGenreDao.add(movieGenreB);

    }

}
