package calculator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("CalculatorGUI.fxml"));
        primaryStage.setTitle("Calculator");
        primaryStage.setScene(new Scene(root, 383, 440));
        primaryStage.setResizable(false); // Disables screen resize
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}