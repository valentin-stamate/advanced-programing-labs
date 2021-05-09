package client_server.database.dao;

import client_server.client.MessageRepresentation;
import client_server.database.DatabaseRunner;
import client_server.database.models.User;
import client_server.database.repository.MessageRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MessageDao extends MessageRepository {

    public List<MessageRepresentation> getUserMessages(User user) {
        List<MessageRepresentation> userMessages = new ArrayList<>();

        String sql = "SELECT uf.username, m.message FROM messages m JOIN users uf on m.user_from = uf.id JOIN users ut ON ut.id = m.user_";

        ResultSet resultSet = DatabaseRunner.getInstance().getSqlResult(sql);

        try {
            while (resultSet.next()) {
                MessageRepresentation messageRepresentation = new MessageRepresentation(resultSet.getString(1), resultSet.getString(2), resultSet.getTimestamp(3));
                userMessages.add(messageRepresentation);
            }

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        return userMessages;
    }

}
