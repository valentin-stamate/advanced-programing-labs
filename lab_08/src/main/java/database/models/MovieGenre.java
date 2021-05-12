package database.models;

public class MovieGenre {
    private String movieId;
    private int genreId;

    public MovieGenre(String movieId, int genreId) {
        this.movieId = movieId;
        this.genreId = genreId;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }

    @Override
    public String toString() {
        return "MovieGenres{" +
                "movieId=" + movieId +
                ", genreId=" + genreId +
                '}';
    }
}

