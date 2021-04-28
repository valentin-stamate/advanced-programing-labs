package client_server.database.models;

public class Friendship {
    private int id;
    private int userA;
    private int userB;

    public Friendship(int userA, int userB) {
        this.userA = userA;
        this.userB = userB;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserA() {
        return userA;
    }

    public void setUserA(int userA) {
        this.userA = userA;
    }

    public int getUserB() {
        return userB;
    }

    public void setUserB(int userB) {
        this.userB = userB;
    }

    @Override
    public String toString() {
        return "Friendship{" +
                "id=" + id +
                ", userA=" + userA +
                ", userB=" + userB +
                '}';
    }
}
