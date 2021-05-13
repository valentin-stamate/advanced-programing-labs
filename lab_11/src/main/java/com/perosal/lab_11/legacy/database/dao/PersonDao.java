package com.perosal.lab_11.legacy.database.dao;

import com.perosal.lab_11.legacy.database.DatabaseRunner;
import com.perosal.lab_11.legacy.database.models.Friendship;
import com.perosal.lab_11.legacy.database.models.Message;
import com.perosal.lab_11.legacy.database.models.Person;
import com.perosal.lab_11.legacy.database.repository.PersonRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersonDao extends PersonRepository {

    public List<Person> getFriends(Person person) {
        List<Person> friendList = new ArrayList<>();

        String sql = String.format("SELECT pfriend.%s FROM %s p JOIN %s pf ON p.%s = pf.%s AND p.%s = '%s' JOIN      %s pfriend ON pfriend.%s = pf.%s",
                Person.ID_COLUMN_NAME, Person.TABLE_NAME, Friendship.TABLE_NAME, Person.ID_COLUMN_NAME, Friendship.USER_COLUMN_NAME, Person.USERNAME_COLUMN_NAME, person.getUsername(),
                Person.TABLE_NAME, Person.ID_COLUMN_NAME, Friendship.FRIEND_COLUMN_NAME
        );

        ResultSet resultSet = DatabaseRunner.getInstance().getSqlResult(sql);

        PersonDao personDao = new PersonDao();

        try {
            while (resultSet.next()) {
                person = personDao.findById(resultSet.getInt(1));
                person.setPassword("");
                friendList.add(person);
            }

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        return friendList;
    }

    public List<Person> getAll() {
        List<Person> people = new ArrayList<>();

        String sql = String.format("SELECT p.%s, p.%s, p.%s FROM %s p",
                Person.ID_COLUMN_NAME, Person.USERNAME_COLUMN_NAME, Person.PASSWORD_COLUMN_NAME, Person.TABLE_NAME);

        ResultSet resultSet = DatabaseRunner.getInstance().getSqlResult(sql);

        try {
            while (resultSet.next()) {
                Person person = new Person(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3));
                people.add(person);
            }

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        return people;
    }

    public void sendMessage(Person person, String userToUsername, String message) {
        PersonDao personDao = new PersonDao();
        MessageDao messageDao = new MessageDao();

        Person personTo = personDao.findByUsername(userToUsername);

        Message messageModel = new Message(person.getId(), message);
        messageDao.save(messageModel);

        messageDao.associateToPerson(personTo, messageModel);
    }

}
