package database.models;

public class Movie {
    private int id;
    private String idMovie;
    private String title;
    private String releaseDate;
    private String duration;
    private float score;
    private int votes;

    public Movie(int id, String idMovie, String title, String releaseDate, String duration, float score, int votes) {
        this.id = id;
        this.idMovie =  idMovie;
        this.title = title;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.score = score;
        this.votes = votes;
    }

    public Movie(String idMovie, String title, String releaseDate, String duration, float score, int votes) {
        this.title = title;
        this.idMovie = idMovie;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.score = score;
        this.votes = votes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public String getIdMovie() {
        return idMovie;
    }

    public void setIdMovie(String idMovie) {
        this.idMovie = idMovie;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", idMovie='" + idMovie + '\'' +
                ", title='" + title + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", duration='" + duration + '\'' +
                ", score=" + score +
                ", votes=" + votes +
                '}';
    }
}