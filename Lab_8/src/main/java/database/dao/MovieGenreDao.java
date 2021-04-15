package database.dao;

import database.DatabaseRunner;
import database.models.MovieGenre;

import java.util.List;

public class MovieGenreDao implements Dao<MovieGenre> {

    @Override
    public void add(MovieGenre movieGenre) {
        String sql = String.format("INSERT INTO movie_genres(movie_id, genre_id) VALUES(%d, %d)", movieGenre.getMovieId(), movieGenre.getGenreId());

        if (DatabaseRunner.getInstance().runSql(sql)) {
            System.out.println("MovieGenre " + movieGenre + " inserted into database");
        }
    }

    @Override
    public List<MovieGenre> getAll() {
        return null;
    }

}
