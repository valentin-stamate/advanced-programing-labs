package database.dao;

import database.DatabaseRunner;
import database.models.Genre;
import database.models.Movie;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GenreDao implements Dao<Genre> {
    @Override
    public void add(Genre genre) {
        String sql = String.format("INSERT INTO genres(name) VALUES('%s')", genre.getName());

        if (DatabaseRunner.getInstance().runSql(sql)) {
            System.out.println("Genre " + genre + " inserted into database");
        }
    }

    public void addIfNotExists(Genre genre) {
        String sql = String.format("SELECT * FROM genres WHERE name = '%s'", genre.getName());

        ResultSet resultSet = DatabaseRunner.getInstance().getSqlResult(sql);

        try {
            if (resultSet == null) {
                return;
            }

            if (!resultSet.next()) {
                add(genre);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public List<Genre> getAll() {
        return null;
    }

    public Genre getGenreByName(String name) {
        Genre genre = null;

        String sql = String.format("SELECT * FROM genres WHERE name = '%s'", name);

        ResultSet resultSet = DatabaseRunner.getInstance().getSqlResult(sql);

        try {
            if (resultSet == null) {
                return null;
            }

            if (!resultSet.next()) {
                return null;
            }

            genre = new Genre(resultSet.getInt(1), resultSet.getString(2));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return genre;
    }

    public List<Genre> getTopGenres() {
        List<Genre> topGenres = new ArrayList<>();

        String sql = "SELECT COUNT(mg.id_movie) movie_count, g.name FROM movie_genres mg JOIN genres g on mg.id_genre = g.id GROUP BY g.name ORDER BY movie_count DESC LIMIT 10";

        ResultSet resultSet = DatabaseRunner.getInstance().getSqlResult(sql);

        try {
            if (resultSet == null) {
                return null;
            }

            while (resultSet.next()) {
                Genre genre = new Genre(resultSet.getInt(1),
                        resultSet.getString(2));
                topGenres.add(genre);
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return topGenres;
    }
}
