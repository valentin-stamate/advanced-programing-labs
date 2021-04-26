
import database.AbstractRepository;
import database.RepositoryFactory;
import database.jdbc.dao.MovieJDBCDao;
import database.jpa.dao.MovieJPADao;
import database.models.Movie;
import org.junit.jupiter.api.Test;

public class TestUnit {
    @Test
    public void addMovie() {
        AbstractRepository<Movie> movieDao = new RepositoryFactory().create(MovieJPADao.getType());
//        AbstractRepository<Movie> movieDao = new RepositoryFactory().create(MovieJDBCDao.getType());

        Movie movie = new Movie("tt0417299", "The Last Airbender", "February 21, 2005", "63 episodes");
        movieDao.save(movie);
        movie.setTitle("Avatar: The Last Airbender");
        movieDao.save(movie);

        System.out.println(movieDao.findById(1));
        System.out.println(movieDao.findByName("Avatar: The Last Airbender"));

    }
}
