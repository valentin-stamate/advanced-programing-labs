package com.perosal.lab_11.legacy.database.repository;

import com.perosal.lab_11.legacy.database.DatabaseRunner;
import com.perosal.lab_11.legacy.database.models.Friendship;

public class FriendshipRepository implements Repository<Friendship>{
    @Override
    public boolean save(Friendship friendship1) {
        String sql1 = String.format("INSERT INTO %s(%s, %s) VALUES(%d, %d)",
                Friendship.TABLE_NAME, Friendship.USER_COLUMN_NAME, Friendship.FRIEND_COLUMN_NAME,
                friendship1.getUserA(), friendship1.getUserB());

        Friendship friendship2 = new Friendship(friendship1.getUserB(), friendship1.getUserA());
        String sql2 = String.format("INSERT INTO %s(%s, %s) VALUES(%d, %d)",
                Friendship.TABLE_NAME, Friendship.USER_COLUMN_NAME, Friendship.FRIEND_COLUMN_NAME,
                friendship2.getUserA(), friendship2.getUserB());

        if (DatabaseRunner.getInstance().runSql(sql1)) {
            System.out.println("Friendship " + friendship1 + " inserted into database");
        }

        if (DatabaseRunner.getInstance().runSql(sql2)) {
            System.out.println("Friendship " + friendship2 + " inserted into database");
        }

        return true;
    }

    @Override
    public Friendship findById(int id) {
        return null;
    }

}
