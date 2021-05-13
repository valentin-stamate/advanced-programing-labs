package com.perosal.lab_11.legacy.database.repository;

import com.perosal.lab_11.legacy.database.DatabaseRunner;
import com.perosal.lab_11.legacy.database.models.Person;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonRepository implements Repository<Person> {

    @Override
    public boolean save(Person person) {
        String sql = String.format("INSERT INTO %s(%s, %s) VALUES('%s', '%s')",
                Person.TABLE_NAME, Person.USERNAME_COLUMN_NAME, Person.PASSWORD_COLUMN_NAME,
                person.getUsername(), person.getPassword());

        if (DatabaseRunner.getInstance().runSql(sql)) {
            System.out.println("User " + person + " inserted into database");
            return true;
        }

        return false;
    }

    @Override
    public Person findById(int id) {
        Person movie = null;

        String sql = String.format("SELECT p.%s, p.%s, p.%s FROM %s p WHERE p.%s = %d",
                Person.ID_COLUMN_NAME, Person.USERNAME_COLUMN_NAME, Person.PASSWORD_COLUMN_NAME, Person.TABLE_NAME,
                Person.ID_COLUMN_NAME, id);

        ResultSet resultSet = DatabaseRunner.getInstance().getSqlResult(sql);

        try {
            if (resultSet == null) {
                return null;
            }

            if (!resultSet.next()) {
                return null;
            }

            movie = new Person(resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getString(3));
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        return movie;
    }

    public Person findByUsername(String username) {
        Person person = null;

        String sql = String.format("SELECT p.%s, p.%s, p.%s FROM %s p WHERE p.%s = '%s'",
                Person.ID_COLUMN_NAME, Person.USERNAME_COLUMN_NAME, Person.PASSWORD_COLUMN_NAME, Person.TABLE_NAME,
                Person.USERNAME_COLUMN_NAME, username);

        ResultSet resultSet = DatabaseRunner.getInstance().getSqlResult(sql);

        try {
            if (resultSet.next()) {
                person = new Person(resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getString(3));
            }

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        return person;
    }

    public boolean updatePerson(Person person, long id) {
        String sql = String.format("UPDATE %s SET %s = '%s', %s = '%s' WHERE %s = %d",
                Person.TABLE_NAME, Person.USERNAME_COLUMN_NAME, person.getUsername(),
                Person.PASSWORD_COLUMN_NAME, person.getPassword(),
                Person.ID_COLUMN_NAME, id);

        if (DatabaseRunner.getInstance().runSql(sql)) {
            System.out.println("User " + person + " updated database");
            return true;
        }

        return false;
    }

    public boolean delete(Person person) {
        System.out.println(person);
        String sql = String.format("DELETE FROM %s WHERE %s = %d",
                Person.TABLE_NAME, Person.ID_COLUMN_NAME, person.getId());

        if (DatabaseRunner.getInstance().runSql(sql)) {
            System.out.println("User " + person + " deleted from database");
            return true;
        }

        return false;
    }
}
