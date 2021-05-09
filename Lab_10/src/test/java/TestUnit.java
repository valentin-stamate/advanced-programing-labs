import client_server.database.dao.UserDao;
import client_server.database.models.User;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class TestUnit {

    @Test
    public void testUserInsertion() {
        User user = new User("ValentinSt", "123456789");
        UserDao userDao = new UserDao();
        userDao.save(user);
    }

    @Test
    public void showUserFriends() {
        UserDao userDao = new UserDao();

        User user = new User(5, "andrei", "123456789");

        List<User> friendList = userDao.getFriends(user);

        System.out.println(friendList.toString());
    }

    @Test
    public void templateGeneration() {
        DatabaseRepresentation databaseRepresentation = new DatabaseRepresentation();
        databaseRepresentation.init();

        databaseRepresentation.export();
        databaseRepresentation.deploy();

    }

}
