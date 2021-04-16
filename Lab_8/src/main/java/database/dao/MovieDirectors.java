package database.dao;

import database.DatabaseRunner;
import database.models.MovieDirector;

import java.util.List;

public class MovieDirectors implements Dao<MovieDirector> {
    @Override
    public void add(MovieDirector movieDirector) {
        String sql = String.format("INSERT INTO movie_actors(id_movie, id_director) VALUES(%d, %d)", movieDirector.getMovieId(), movieDirector.getDirectorId());

        if (DatabaseRunner.getInstance().runSql(sql)) {
            System.out.println("Director " + movieDirector + " inserted into database");
        }
    }

    @Override
    public List<MovieDirector> getAll() {
        return null;
    }
}
