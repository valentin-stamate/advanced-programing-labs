package database.models;

public class MovieDirector {
    private String movieId;
    private int directorId;

    public MovieDirector(String movieId, int directorId) {
        this.movieId = movieId;
        this.directorId = directorId;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public int getDirectorId() {
        return directorId;
    }

    public void setDirectorId(int directorId) {
        this.directorId = directorId;
    }

    @Override
    public String toString() {
        return "MovieDirector{" +
                "movieId=" + movieId +
                ", directorId=" + directorId +
                '}';
    }
}
