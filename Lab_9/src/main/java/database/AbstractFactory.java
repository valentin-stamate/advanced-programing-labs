package database;

import database.models.Movie;

public interface AbstractFactory {
    AbstractRepository<Movie> create(String type);
}
