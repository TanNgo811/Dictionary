package application;

import code.Dictionary;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static Dictionary mainDictionary = new Dictionary();

    @Override
    public void start(Stage primaryStage) throws Exception{
//        window = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("fxml/MainScene.fxml"));
        primaryStage.setTitle("Dictionary");
        primaryStage.setScene(new Scene(root, 900, 600));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
