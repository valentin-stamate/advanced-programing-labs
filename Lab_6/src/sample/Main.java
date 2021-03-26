package sample;

import javafx.application.Application;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    Stage primaryStage;
    int shapesWidth = 100;
    int shapesHeight = 100;
    int strokeWeight = 2;

    @Override
    public void start(Stage stage) throws Exception{
        primaryStage = stage;
        primaryStage.setOnCloseRequest(e -> {
            e.consume();
            closeProgram();
        });

        Parent root = FXMLLoader.load(getClass().getResource("../layout/sample.fxml"));
        Scene scene = new Scene(root);

        primaryStage.setTitle("Lab 6");
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(Event::consume);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    private void closeProgram() {
        System.out.println("Closing the program");
        primaryStage.close();
    }
}
