import client_server.database.dao.UserDao;
import client_server.database.models.User;
import org.junit.jupiter.api.Test;

import java.util.List;

public class TestUnit {

    @Test
    public void testUserInsertion() {
        User user = new User("ValentinSt", "123456789");
        UserDao userDao = new UserDao();
        userDao.save(user);
    }

    @Test void showUserFriends() {
        UserDao userDao = new UserDao();

        User user = new User(5, "andrei", "123456789");

        List<User> friendList = userDao.getFriends(user);

        System.out.println(friendList.toString());
    }

}
