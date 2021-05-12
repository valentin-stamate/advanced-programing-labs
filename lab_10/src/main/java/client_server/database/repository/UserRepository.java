package client_server.database.repository;

import client_server.database.DatabaseRunner;
import client_server.database.models.User;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository implements Repository<User> {

    @Override
    public void save(User user) {
        String sql = String.format("INSERT INTO users(username, password) VALUES('%s', '%s')", user.getUsername(), user.getPassword());

        if (DatabaseRunner.getInstance().runSql(sql)) {
            System.out.println("User " + user + " inserted into database");
        }
    }

    @Override
    public User findById(int id) {
        User movie = null;

        String sql = String.format("SELECT * FROM users WHERE id = %d", id);

        ResultSet resultSet = DatabaseRunner.getInstance().getSqlResult(sql);

        try {
            if (resultSet == null) {
                return null;
            }

            if (!resultSet.next()) {
                return null;
            }

            movie = new User(resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getString(3));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return movie;
    }

    public User findByUsername(String username) {
        User user = null;

        String sql = String.format("SELECT * FROM users WHERE username = '%s'", username);

        ResultSet resultSet = DatabaseRunner.getInstance().getSqlResult(sql);

        try {
            if (resultSet.next()) {
                user = new User(resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getString(3));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return user;
    }

}
