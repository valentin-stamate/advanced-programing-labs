import dao.MovieDao;
import entities.Movie;
import org.junit.jupiter.api.Test;
import repository.Manager;

public class TestUnit {
    @Test
    public void addMovie() {
        MovieDao movieDao = new MovieDao();

        Movie movie = new Movie("tt0417299", "The Last Airbender", "February 21, 2005", "63 episodes");
        movieDao.save(movie);
        movie.setTitle("Avatar: The Last Airbender");
        movieDao.save(movie);

        System.out.println(movieDao.findById(1));

        System.out.println(movieDao.findByName("idMovie", "tt0417299"));

    }
}
