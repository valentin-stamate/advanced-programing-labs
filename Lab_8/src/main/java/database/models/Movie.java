package database.models;

import java.sql.Timestamp;

public class Movie {
    private int id;
    private String title;
    private Timestamp release_date;
    private String duration;
    private float score;

    public Movie(int id, String title, Timestamp release_date, String duration, float score) {
        this.id = id;
        this.title = title;
        this.release_date = release_date;
        this.duration = duration;
        this.score = score;
    }
    public Movie(String title, Timestamp release_date, String duration, float score) {
        this.title = title;
        this.release_date = release_date;
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

    public Timestamp getRelease_date() {
        return release_date;
    }

    public void setRelease_date(Timestamp release_date) {
        this.release_date = release_date;
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

    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", release_date=" + release_date +
                ", duration='" + duration + '\'' +
                ", score=" + score +
                '}';
    }

}