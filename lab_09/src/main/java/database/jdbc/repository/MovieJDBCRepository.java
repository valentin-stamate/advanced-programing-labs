package database.jdbc.repository;

import database.AbstractRepository;
import database.jdbc.DatabaseRunner;
import database.models.Movie;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MovieJDBCRepository implements AbstractRepository<Movie> {
    @Override
    public void save(Movie movie) {
        String sql = String.format("INSERT INTO movies(id_movie, title, release_date, duration, score, votes) VALUES('%s', '%s', '%s', '%s', %f, %d)",
                movie.getIdMovie(), movie.getTitle(), movie.getReleaseDate(), movie.getDuration(), movie.getScore(), movie.getVotes());

        if (DatabaseRunner.getInstance().runSql(sql)) {
            System.out.println("Movie " + movie + " inserted into database");
        }
    }

    @Override
    public Movie findById(int id) {
        Movie movie = null;

        String sql = String.format("SELECT * FROM movies WHERE id = %d", id);

        ResultSet resultSet = DatabaseRunner.getInstance().getSqlResult(sql);

        try {
            if (resultSet == null) {
                return null;
            }

            if (!resultSet.next()) {
                return null;
            }

            movie = new Movie(resultSet.getInt(1),
                    resultSet.getString(3),
                    resultSet.getString(6),
                    resultSet.getString(4),
                    resultSet.getString(2),
                    resultSet.getFloat(5),
                    resultSet.getInt(7));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return movie;
    }

    @Override
    public List<Movie> findByName(String name) {
        List<Movie> movies = new ArrayList<>();

        String sql = String.format("SELECT * FROM movies WHERE title = '%s'", name);

        ResultSet resultSet = DatabaseRunner.getInstance().getSqlResult(sql);

        try {
            while (resultSet.next()) {
                Movie movie = new Movie(resultSet.getInt(1),
                        resultSet.getString(3),
                        resultSet.getString(6),
                        resultSet.getString(4),
                        resultSet.getString(2),
                        resultSet.getFloat(5),
                        resultSet.getInt(7));

                movies.add(movie);
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return movies;
    }

    public static String getType() {
        return "MovieJDBC";
    }
}
