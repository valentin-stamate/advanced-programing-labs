import client_server.database.dao.UserDao;
import client_server.database.models.User;
import org.junit.jupiter.api.Test;

public class TestUnit {

    @Test
    public void testUserInsertion() {
        User user = new User("ValentinSt", "123456789");
        UserDao userDao = new UserDao();
        userDao.save(user);
    }

}
