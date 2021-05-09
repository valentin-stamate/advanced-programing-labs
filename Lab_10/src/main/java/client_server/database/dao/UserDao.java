package client_server.database.dao;

import client_server.database.DatabaseRunner;
import client_server.database.models.Friendship;
import client_server.database.models.Message;
import client_server.database.models.User;
import client_server.database.repository.UserRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao extends UserRepository {

    public List<User> getFriends(User user) {
        List<User> friendList = new ArrayList<>();

        String sql = String.format("SELECT ub.id FROM users ua JOIN friendships f on ua.id = f.user_a AND ua.id=%d JOIN users ub ON f.user_b = ub.id;", user.getId());

        ResultSet resultSet = DatabaseRunner.getInstance().getSqlResult(sql);

        UserDao userDao = new UserDao();

        try {
            while (resultSet.next()) {
                user = userDao.findById(resultSet.getInt(1));
                friendList.add(user);
            }

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        return friendList;
    }

    public void addFriends(User userA, String[] usernames) {
        UserDao user = new UserDao();
        FriendShipDao friendShipDao = new FriendShipDao();

        for (String username : usernames) {
            User userB = user.findByUsername(username);

            friendShipDao.save(new Friendship(userA.getId(), userB.getId()));
        }
    }

    public List<Message> getMessagesFromUser(User user, User userFrom) {
        List<Message> messages = new ArrayList<>();

        String sql = String.format("SELECT * FROM messages WHERE user = %d AND user_from = %d ORDER BY date_created", user.getId(), userFrom.getId());

        ResultSet resultSet = DatabaseRunner.getInstance().getSqlResult(sql);

        try {
            while (resultSet.next()) {
                Message message = new Message(resultSet.getInt(2), resultSet.getInt(3),
                        resultSet.getString(4), resultSet.getTimestamp(5));

                messages.add(message);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return messages;
    }

    public void sendMessage(User user, String userToUsername, String message) {
        UserDao userDao = new UserDao();
        User userTo = userDao.findByUsername(userToUsername);

        Message messageModel = new Message(user.getId(), userTo.getId(), message);

        MessageDao messageDao = new MessageDao();
        messageDao.save(messageModel);
    }

}
