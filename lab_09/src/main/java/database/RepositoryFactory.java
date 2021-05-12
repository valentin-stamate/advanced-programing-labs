package database;

import configuration_reader.ConfigurationReader;
import database.jdbc.repository.MovieJDBCRepository;
import database.jpa.repository.MovieJPARepository;
import database.models.Movie;

import java.util.Locale;

public class RepositoryFactory implements AbstractFactory {

    @Override
    public AbstractRepository<Movie> create() {
        String type = String.format("Movie%s", ConfigurationReader.readImplementationType().toUpperCase(Locale.ROOT));

        if (type.equals(MovieJDBCRepository.getType())) {
            return new MovieJDBCRepository();
        }

        return new MovieJPARepository();
    }
}
