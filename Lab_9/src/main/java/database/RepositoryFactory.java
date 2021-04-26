package database;

import database.jdbc.repository.MovieJDBCRepository;
import database.jpa.repository.MovieJPARepository;
import database.models.Movie;

public class RepositoryFactory implements AbstractFactory {

    @Override
    public AbstractRepository<Movie> create(String type) {
        if (type.equals(MovieJDBCRepository.getType())) {
            return new MovieJDBCRepository();
        }

        return new MovieJPARepository();
    }
}
