package client_server.database.repository;

public interface Repository<T>{
    void save(T t);
    T findById(int id);
}
