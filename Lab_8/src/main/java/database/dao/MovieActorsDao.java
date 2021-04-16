package database.dao;

import database.DatabaseRunner;
import database.models.MovieActor;

import java.util.List;

public class MovieActorsDao implements Dao<MovieActor> {
    @Override
    public void add(MovieActor movieActor) {
        String sql = String.format("INSERT INTO movie_actors(id_movie, id_actor) VALUES(%d, %d)", movieActor.getMovieId(), movieActor.getActorId());

        if (DatabaseRunner.getInstance().runSql(sql)) {
            System.out.println("MovieActor " + movieActor + " inserted into database");
        }
    }

    @Override
    public List<MovieActor> getAll() {
        return null;
    }
}
