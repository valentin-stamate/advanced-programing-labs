package database.models;

public class MovieActor {
    private String movieId;
    private int actorId;

    public MovieActor(String movieId, int actorId) {
        this.movieId = movieId;
        this.actorId = actorId;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public int getActorId() {
        return actorId;
    }

    public void setActorId(int actorId) {
        this.actorId = actorId;
    }

    @Override
    public String toString() {
        return "MovieActor{" +
                "movieId=" + movieId +
                ", actorId=" + actorId +
                '}';
    }
}
