package database.dao;

import database.DatabaseRunner;
import database.models.Actor;
import database.models.Movie;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ActorDao implements Dao<Actor> {
    @Override
    public void add(Actor actor) {
        String sql = String.format("INSERT INTO actors(name) VALUES('%s')", actor.getName());

        if (DatabaseRunner.getInstance().runSql(sql)) {
            System.out.println("Actor " + actor + " inserted into database");
        }
    }

    public void addIfNotExists(Actor actor) {
        String sql = String.format("SELECT * FROM actors WHERE name = '%s'", actor.getName());

        ResultSet resultSet = DatabaseRunner.getInstance().getSqlResult(sql);

        try {
            if (resultSet == null) {
                System.out.println("Result set is null");
                return;
            }

            if (!resultSet.next()) {
                add(actor);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public Actor getActorByName(String name) {
        Actor actor = null;

        String sql = String.format("SELECT * FROM actors WHERE name = '%s'", name);

        ResultSet resultSet = DatabaseRunner.getInstance().getSqlResult(sql);

        try {
            if (resultSet == null) {
                return null;
            }

            if (!resultSet.next()) {
                return null;
            }

            actor = new Actor(resultSet.getInt(1),
                    resultSet.getString(2));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return actor;
    }

    @Override
    public List<Actor> getAll() {
        return null;
    }
}
