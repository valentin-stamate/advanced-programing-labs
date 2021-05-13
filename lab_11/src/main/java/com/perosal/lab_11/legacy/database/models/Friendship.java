package com.perosal.lab_11.legacy.database.models;

public class Friendship {

    public static final String TABLE_NAME = "persons_friends";
    public static final String USER_COLUMN_NAME = "person_model_id";
    public static final String FRIEND_COLUMN_NAME = "friends_id";

    private long userA;
    private long userB;

    public Friendship(long userA, long userB) {
        this.userA = userA;
        this.userB = userB;
    }

    public long getUserA() {
        return userA;
    }

    public void setUserA(int userA) {
        this.userA = userA;
    }

    public long getUserB() {
        return userB;
    }

    public void setUserB(int userB) {
        this.userB = userB;
    }

    @Override
    public String toString() {
        return "Friendship{" +
                ", userA=" + userA +
                ", userB=" + userB +
                '}';
    }
}
