package com.perosal.lab_11.legacy.database.dao;

import com.perosal.lab_11.legacy.MessageRepresentation;
import com.perosal.lab_11.legacy.database.DatabaseRunner;
import com.perosal.lab_11.legacy.database.models.Message;
import com.perosal.lab_11.legacy.database.models.Person;
import com.perosal.lab_11.legacy.database.repository.MessageRepository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MessageDao extends MessageRepository {

    public List<MessageRepresentation> getUserMessages(Person person) {
        List<MessageRepresentation> userMessages = new ArrayList<>();

        String sql = String.format("SELECT m.%s, p.%s FROM %s p JOIN %s pm on p.%s = pm.%s AND     p.%s = '%s' JOIN %s m ON m.%s = pm.%s",
                Message.MESSAGE_COLUMN_NAME, Person.USERNAME_COLUMN_NAME, Person.TABLE_NAME, "persons_messages", Person.ID_COLUMN_NAME, "person_model_id",
                Person.USERNAME_COLUMN_NAME, person.getUsername(), Message.TABLE_NAME, Message.ID_COLUMN_NAME, "messages_id");

        ResultSet resultSet = DatabaseRunner.getInstance().getSqlResult(sql);

        try {
            while (resultSet.next()) {
                MessageRepresentation messageRepresentation = new MessageRepresentation(resultSet.getString(1), resultSet.getString(2));
                userMessages.add(messageRepresentation);
            }

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        return userMessages;
    }

}
