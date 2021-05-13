package com.perosal.lab_11.legacy.database.repository;

import com.perosal.lab_11.legacy.database.DatabaseRunner;
import com.perosal.lab_11.legacy.database.models.Message;
import com.perosal.lab_11.legacy.database.models.Person;

public class MessageRepository implements Repository<Message>{
    @Override
    public boolean save(Message message) {
        String sql = String.format("INSERT INTO %s(%s, %s) VALUES('%s', %d)",
                Message.TABLE_NAME, Message.MESSAGE_COLUMN_NAME, Message.FROM_COLUMN_NAME,
                message.getMessage(), message.getUserFrom());

        if (DatabaseRunner.getInstance().runSql(sql)) {
            System.out.println("Message " + message + " inserted into database");
            return true;
        }

        return false;
    }

    public void associateToPerson(Person person, Message message) {
        String sql = String.format("INSERT INTO %s(%s, %s) VALUES(%d, %d)",
                "person_messages", Message.FROM_COLUMN_NAME, "messages_id",
                person.getId(), message.getId());

        if (DatabaseRunner.getInstance().runSql(sql)) {
            System.out.println("Message " + message + " associated into database");
        }
    }

    @Override
    public Message findById(int id) {
        return null;
    }

}
