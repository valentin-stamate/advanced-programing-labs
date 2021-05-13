package com.perosal.lab_11.legacy.database.repository;

public interface Repository<T>{
    boolean save(T t);
    T findById(int id);
}
