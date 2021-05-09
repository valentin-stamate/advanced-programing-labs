package client_server.database.repository;

import client_server.database.DatabaseRunner;
import client_server.database.models.Friendship;

public class FriendshipRepository implements Repository<Friendship>{
    @Override
    public void save(Friendship friendship1) {
        String sql1 = String.format("INSERT INTO friendships(user_a, user_b) VALUES(%d, %d)", friendship1.getUserA(), friendship1.getUserB());

        Friendship friendship2 = new Friendship(friendship1.getUserB(), friendship1.getUserA());
        String sql2 = String.format("INSERT INTO friendships(user_a, user_b) VALUES(%d, %d)", friendship2.getUserA(), friendship2.getUserB());

        if (DatabaseRunner.getInstance().runSql(sql1)) {
            System.out.println("Friendship " + friendship1 + " inserted into database");
        }

        if (DatabaseRunner.getInstance().runSql(sql2)) {
            System.out.println("Friendship " + friendship2 + " inserted into database");
        }
    }

    @Override
    public Friendship findById(int id) {
        return null;
    }

}
