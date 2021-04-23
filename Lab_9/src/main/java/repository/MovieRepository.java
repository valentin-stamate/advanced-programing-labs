package repository;

import entities.Movie;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

public abstract class MovieRepository {

    public void save(Movie movie) {
        Manager.getInstance().saveObject(movie);
    }

    public Movie findById(int id) {
        String sql = String.format("SELECT m FROM Movie m WHERE m.id = %d", id);
        TypedQuery<Movie> query = Manager.getInstance().getEntityManager().createQuery(sql, Movie.class);

        return query.getSingleResult();
    }

    public List<Movie> findByName(String columnName, String columnValue) {
        String sql = String.format("SELECT m FROM Movie m WHERE m.%s = '%s'", columnName, columnValue);
        TypedQuery<Movie> query = Manager.getInstance().getEntityManager().createQuery(sql, Movie.class);

        return query.getResultList();
    }

}
