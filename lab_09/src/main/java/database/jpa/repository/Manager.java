package database.jpa.repository;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class Manager {

    private static Manager instance = null;
    private final EntityManager entityManager;

    private Manager() {
        this.entityManager = Persistence.createEntityManagerFactory("jpa").createEntityManager();
    }

    public static Manager getInstance() {
        if (instance == null) {
            instance = new Manager();
        }

        return instance;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void saveObject(Object object) {
        entityManager.getTransaction().begin();
        entityManager.persist(object);
        entityManager.getTransaction().commit();
    }

    public void close() {
        entityManager.close();
    }

}
