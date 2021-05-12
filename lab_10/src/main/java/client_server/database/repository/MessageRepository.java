package client_server.database.repository;

import client_server.database.DatabaseRunner;
import client_server.database.models.Message;

public class MessageRepository implements Repository<Message>{
    @Override
    public void save(Message message) {
        String sql = String.format("INSERT INTO messages(user_, user_from, message, time_created) VALUES(%d, %d, '%s', '%s')",
                message.getUser(), message.getUserFrom(), message.getMessage(), message.getTimestamp().toString());

        if (DatabaseRunner.getInstance().runSql(sql)) {
            System.out.println("Message " + message + " inserted into database");
        }
    }

    @Override
    public Message findById(int id) {
        return null;
    }

}
