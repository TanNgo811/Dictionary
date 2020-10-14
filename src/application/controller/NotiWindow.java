package application.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class NotiWindow {
    private String message;

    public NotiWindow() {
        this.message = "";
    }

    public NotiWindow(String message) {
        this.message = message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static void openAlertWindow(String message) {
        NotiWindow alertWindow = new NotiWindow(message);
        try {
            alertWindow.run();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public void run() throws Exception
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/NotiWindow.fxml"));

        NotiController controller = new NotiController(message);

        loader.setController(controller);

        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("Notification");
        stage.setScene(new Scene(root, 300, 200));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.showAndWait();
    }
}
