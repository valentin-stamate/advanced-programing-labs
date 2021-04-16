package database.dao;

import database.DatabaseRunner;
import database.models.Movie;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MovieDao implements Dao<Movie> {

    @Override
    public void add(Movie movie) {
        String sql = String.format("INSERT INTO movies(id_movie, title, release_date, duration, score, votes) VALUES('%s', '%s', '%s', '%s', %f, %d)",
                movie.getIdMovie(), movie.getTitle(), movie.getReleaseDate(), movie.getDuration(), movie.getScore(), movie.getVotes());

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
                    resultSet.getFloat(6),
                    resultSet.getInt(7));
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
                    resultSet.getFloat(6),
                    resultSet.getInt(7));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return movie;
    }

    public List<Movie> getTopMovies(boolean asc) {
        List<Movie> topMovies = new ArrayList<>();

        String sql = "SELECT * FROM movies votes WHERE votes > 1000000 ORDER BY score DESC LIMIT 10";

        if (asc) {
            sql = "SELECT * FROM movies votes WHERE votes > 100000 ORDER BY score LIMIT 10";
        }

        ResultSet resultSet = DatabaseRunner.getInstance().getSqlResult(sql);

        try {
            if (resultSet == null) {
                return null;
            }

            while (resultSet.next()) {
                Movie movie = new Movie(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getFloat(6),
                        resultSet.getInt(7));
                topMovies.add(movie);
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return topMovies;
    }

}
