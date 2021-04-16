package database.dao;

import database.DatabaseRunner;
import database.models.Movie;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MovieDao implements Dao<Movie> {

    @Override
    public void add(Movie movie) {
        String sql = String.format("INSERT INTO movies(id_movie, title, release_date, duration, score) VALUES('%s', '%s', '%s', '%s', %f)",
                movie.getIdMovie(), movie.getTitle(), movie.getReleaseDate(), movie.getDuration(), movie.getScore());

        if (DatabaseRunner.getInstance().runSql(sql)) {
            System.out.println("Movie " + movie + " inserted into database");
        }
    }

    @Override
    public List<Movie> getAll() {
        return null;
    }

    public Movie getMovieByTitle(String title) {
        Movie movie = null;

        String sql = String.format("SELECT * FROM movies WHERE title = '%s'", title);

        ResultSet resultSet = DatabaseRunner.getInstance().getSqlResult(sql);

        try {
            if (!resultSet.next()) {
                return null;
            }

            movie = new Movie(resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getFloat(6));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return movie;
    }

    public void addIfNotExists(Movie movie) {
        String sql = String.format("SELECT * FROM movies WHERE id_movie = '%s'", movie.getIdMovie());

        ResultSet resultSet = DatabaseRunner.getInstance().getSqlResult(sql);

        try {
            if (resultSet == null) {
                return;
            }

            if (!resultSet.next()) {
                add(movie);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /* TODO, DRY yourself */
    public Movie getMovieByMovieId(String idMovie) {
        Movie movie = null;

        String sql = String.format("SELECT * FROM movies WHERE id_movie = '%s'", idMovie);

        ResultSet resultSet = DatabaseRunner.getInstance().getSqlResult(sql);

        try {
            if (resultSet == null) {
                return null;
            }

            if (!resultSet.next()) {
                return null;
            }

            movie = new Movie(resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getFloat(6));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return movie;
    }

}
