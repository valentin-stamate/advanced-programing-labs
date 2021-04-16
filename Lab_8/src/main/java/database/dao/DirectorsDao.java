package database.dao;

import database.DatabaseRunner;
import database.models.Director;
import database.models.Movie;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DirectorsDao implements Dao<Director> {
    @Override
    public void add(Director director) {
        String sql = String.format("INSERT INTO directors(name) VALUES('%s')", director.getName());

        if (DatabaseRunner.getInstance().runSql(sql)) {
            System.out.println("Director " + director + " inserted into database");
        }
    }

    public void addIfNotExists(Director director) {
        String sql = String.format("SELECT * FROM directors WHERE name = '%s'", director.getName());

        ResultSet resultSet = DatabaseRunner.getInstance().getSqlResult(sql);

        try {
            if (resultSet == null) {
                System.out.println("Result set is null");
                return;
            }

            if (!resultSet.next()) {
                add(director);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Director getDirectorByName(String name) {
        Director director = null;

        String sql = String.format("SELECT * FROM directors WHERE name = '%s'", name);

        ResultSet resultSet = DatabaseRunner.getInstance().getSqlResult(sql);

        try {
            if (resultSet == null) {
                return null;
            }

            if (!resultSet.next()) {
                return null;
            }

            director = new Director(resultSet.getInt(1),
                    resultSet.getString(2));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return director;
    }

    @Override
    public List<Director> getAll() {
        return null;
    }

}
