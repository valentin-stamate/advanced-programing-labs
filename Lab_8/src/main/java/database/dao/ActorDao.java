package database.dao;

import database.DatabaseRunner;
import database.models.Actor;

import java.util.List;

public class ActorDao implements Dao<Actor> {
    @Override
    public void add(Actor actor) {
        String sql = String.format("INSERT INTO actors(name) VALUES('%s')", actor.getName());

        if (DatabaseRunner.getInstance().runSql(sql)) {
            System.out.println("Actor " + actor + " inserted into database");
        }
    }

    @Override
    public List<Actor> getAll() {
        return null;
    }
}
