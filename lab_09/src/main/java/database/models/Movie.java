package database.models;

import javax.persistence.*;

@Entity
@Table(name = "movies")
@NamedQueries({
        @NamedQuery(name = "Movie.findById", query = "SELECT m FROM Movie m WHERE m.id = :id"),
        @NamedQuery(name = "Movie.findByName", query = "SELECT m FROM Movie m WHERE m.title = :name")
})
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "id_movie", unique = true)
    private String idMovie;
    @Column(unique = true)
    private String title;
    @Column(name = "release_date")
    private String releaseDate;
    private String duration;
    private float score = 0;
    private int votes = 0;

    public Movie(String idMovie, String title, String releaseDate, String duration) {
        this.idMovie = idMovie;
        this.title = title;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }

    public Movie(int id, String idMovie, String title, String releaseDate, String duration, float score, int votes) {
        this.id = id;
        this.idMovie = idMovie;
        this.title = title;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.score = score;
        this.votes = votes;
    }

    public Movie() { }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdMovie() {
        return idMovie;
    }

    public void setIdMovie(String idMovie) {
        this.idMovie = idMovie;
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

    public double getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
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
