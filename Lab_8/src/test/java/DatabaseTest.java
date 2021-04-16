import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import database.dao.GenreDao;
import database.dao.MovieDao;
import database.dao.MovieGenreDao;
import database.models.Genre;
import database.models.Movie;
import database.models.MovieGenre;
import dataset.DataSetParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseTest {

    @Test
    public void insertMovie() {
        MovieDao movieDao = new MovieDao();
        GenreDao genreDao = new GenreDao();
        MovieGenreDao movieGenreDao = new MovieGenreDao();

        Movie movie = new Movie("tt110110", "Avatar", new Timestamp(new Date().getTime()).toString(), "1h", 10, 10);
        movieDao.add(movie);

        Genre genreA = new Genre("drama");
        Genre genreB = new Genre("action");
        genreDao.addIfNotExists(genreA);
        genreDao.addIfNotExists(genreB);

        Movie desiredMovie = movieDao.getMovieByTitle("Avatar");
        genreA = genreDao.getGenreByName("drama");
        genreB = genreDao.getGenreByName("action");

        MovieGenre movieGenreA = new MovieGenre(desiredMovie.getIdMovie(), genreA.getId());
        MovieGenre movieGenreB = new MovieGenre(desiredMovie.getIdMovie(), genreB.getId());

        movieGenreDao.add(movieGenreA);
        movieGenreDao.add(movieGenreB);

    }

    @Test
    public void getCSVData() throws IOException, CsvValidationException {
        CSVReader csvReader = new CSVReader(new FileReader("C:\\Users\\Valentin\\Desktop\\Sem.ll\\Programare Avansata\\Laborator\\Laborator-Java\\Lab_8\\src\\main\\java\\dataset\\movies.csv"));

        List<List<String>> records = new ArrayList<>();

        String[] row = null;
        int i = 0;
        while ((row = csvReader.readNext()) != null) {
            records.add(List.of(row));
            System.out.println(records.get(i++));
        }
    }

    @Test
    public void putDataIntoDatabaseTest() {
        try {
            DataSetParser.putDataInDatabase();
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void trimTest() {
        Assertions.assertEquals("John ''Wich", "   John     'Wich   ".replaceAll("\\s+", " ").trim().replaceAll("'", "''"));
    }

}
