import database.dataset_statistics.DatasetStatistics;
import org.junit.jupiter.api.Test;

public class DatasetStatisticsReport {
    @Test
    void movieStatisticsTest() {
        DatasetStatistics movieStatistics = new DatasetStatistics();
        movieStatistics.init();

        movieStatistics.generateStatistics();
    }
}
