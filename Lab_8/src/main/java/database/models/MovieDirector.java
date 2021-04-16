package database.models;

public class MovieDirector {
    private int movieId;
    private int directorId;

    public MovieDirector(int movieId, int directorId) {
        this.movieId = movieId;
        this.directorId = directorId;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
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
