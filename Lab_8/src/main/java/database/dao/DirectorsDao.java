package database.dao;

import database.DatabaseRunner;
import database.models.Director;

import java.util.List;

public class Directors implements Dao<Director> {
    @Override
    public void add(Director director) {
        String sql = String.format("INSERT INTO directors(name) VALUES('%s')", director.getName());

        if (DatabaseRunner.getInstance().runSql(sql)) {
            System.out.println("Director " + director + " inserted into database");
        }
    }

    @Override
    public List<Director> getAll() {
        return null;
    }
}
