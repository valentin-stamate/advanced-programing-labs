package database.dao;

import database.DatabaseRunner;
import database.models.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class GenreDao implements Dao<Genre> {
    @Override
    public void add(Genre genre) {
        String sql = String.format("INSERT INTO genres(name) VALUES('%s')", genre.getName());

        if (DatabaseRunner.getInstance().runSql(sql)) {
            System.out.println("Genre " + genre + " inserted into database");
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
            if (!resultSet.next()) {
                return null;
            }

            genre = new Genre(resultSet.getInt(1), resultSet.getString(2));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return genre;
    }

}
