package client_server.database.repository;

import client_server.database.DatabaseRunner;
import client_server.database.models.Friendship;

public class FriendshipRepository implements Repository<Friendship>{
    @Override
    public void save(Friendship friendship) {
        String sql = String.format("INSERT INTO friendships(user_a, user_b) VALUES(%d, %d)", friendship.getUserA(), friendship.getUserB());

        if (DatabaseRunner.getInstance().runSql(sql)) {
            System.out.println("Friendship " + friendship + " inserted into database");
        }
    }

    @Override
    public Friendship findById(int id) {
        return null;
    }

}
