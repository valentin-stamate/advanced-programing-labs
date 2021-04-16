package database.dao;

import database.DatabaseRunner;
import database.dao.instances.ActorActivity;
import database.models.Actor;
import database.models.Movie;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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

    public List<ActorActivity> getTopActiveActors() {
        List<ActorActivity> actors = new ArrayList<>();

        String sql = "SELECT COUNT(ma.id_movie) activity, a.name FROM movie_actors ma JOIN actors a on ma.id_actor = a.id GROUP BY a.name ORDER BY activity DESC LIMIT 10;";

        ResultSet resultSet = DatabaseRunner.getInstance().getSqlResult(sql);

        try {
            if (resultSet == null) {
                return null;
            }

            while (resultSet.next()) {
                ActorActivity actorActivity = new ActorActivity(resultSet.getString(2),
                        resultSet.getInt(1));
                actors.add(actorActivity);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return actors;
    }
}
