package database.jpa.repository;

import database.AbstractRepository;
import database.models.Movie;
import javax.persistence.TypedQuery;
import java.util.List;

public class MovieJPARepository implements AbstractRepository<Movie> {

    @Override
    public void save(Movie movie) {
        Manager.getInstance().saveObject(movie);
    }

    @Override
    public Movie findById(int id) {
        TypedQuery<Movie> query = Manager.getInstance().getEntityManager()
                .createNamedQuery("Movie.findById", Movie.class)
                .setParameter("id", id);

        return query.getSingleResult();
    }

    @Override
    public List<Movie> findByName(String name) {
        TypedQuery<Movie> query = Manager.getInstance().getEntityManager()
                .createNamedQuery("Movie.findByName", Movie.class)
                .setParameter("name", name);

        return query.getResultList();
    }

    public static String getType() {
        return "MovieJPA";
    }
}
