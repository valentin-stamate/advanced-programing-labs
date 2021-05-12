import com.opencsv.exceptions.CsvValidationException;
import database.dataset_statistics.DatasetStatistics;
import dataset.DataSetParser;

import java.io.IOException;

public class MainClass {

    public static void main(String[] args) {

    }

    void generateStatisticsReport() {
        DatasetStatistics movieStatistics = new DatasetStatistics();
        movieStatistics.init();

        movieStatistics.generateStatistics();
    }

    public void putDataIntoDatabaseTest() {
        try {
            DataSetParser.putDataInDatabase();
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
    }

}
