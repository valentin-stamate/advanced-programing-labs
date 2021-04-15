package database.dao;

import database.DatabaseRunner;
import database.models.Movie;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MovieDao implements Dao<Movie> {

    @Override
    public void add(Movie movie) {
        String sql = String.format("INSERT INTO movies(title, release_date, duration, score) VALUES('%s', '%s', '%s', %f)",
                movie.getTitle(), movie.getRelease_date().toString(), movie.getDuration(), movie.getScore());

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
                    resultSet.getTimestamp(3),
                    resultSet.getString(4),
                    resultSet.getFloat(5));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return movie;
    }

}
