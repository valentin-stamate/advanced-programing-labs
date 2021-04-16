package database.dao.instances;

public class ActorActivity {
    private final String actorName;
    private final int activity;

    public ActorActivity(String actorName, int activity) {
        this.actorName = actorName;
        this.activity = activity;
    }

    public String getActorName() {
        return actorName;
    }

    public int getActivity() {
        return activity;
    }
}
