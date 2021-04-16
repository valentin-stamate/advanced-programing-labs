package database.models;

import java.sql.Timestamp;

public class Movie {
    private int id;
    private String idMovie;
    private String title;
    private String releaseDate;
    private String duration;
    private float score;

    public Movie(int id, String idMovie, String title, String releaseDate, String duration, float score) {
        this.id = id;
        this.idMovie =  idMovie;
        this.title = title;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.score = score;
    }
    public Movie(String idMovie, String title, String releaseDate, String duration, float score) {
        this.title = title;
        this.idMovie = idMovie;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.score = score;
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

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", idMovie='" + idMovie + '\'' +
                ", title='" + title + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", duration='" + duration + '\'' +
                ", score=" + score +
                '}';
    }
}