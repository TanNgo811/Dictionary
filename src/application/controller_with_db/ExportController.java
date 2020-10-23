package application.controller_with_db;

import code.Word;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static application.controller.NotiWindow.openAlertWindow;
import static code.DictionaryManagement.wordsExportToFile;

public class ExportController implements Initializable {

    @FXML
    public Button btnCancel;

    @FXML
    public Button btnConfirm;

    @FXML
    public TextField tfFileName;

    @FXML
    public ListView lvSearchWords;

    ArrayList<Word> searchedWords;

    public void setLVSearchWords(ArrayList<Word> words) {
        searchedWords = words;
        for (Word i : searchedWords) {
            lvSearchWords.getItems().add(i.getWordTarget());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setStyleOnHover(btnConfirm);
        setStyleOnHover(btnCancel);

    }

    public void confirmExport(ActionEvent actionEvent) throws IOException {
        String fileName = tfFileName.getText();
        wordsExportToFile(searchedWords, fileName);
        goBack(actionEvent);
        openAlertWindow("Exported to File Successfully!");
    }

    public void goBack (ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../fxml/MainScene.fxml"));
        Parent mainParent = loader.load();
        Scene scene = new Scene(mainParent);
        stage.setScene(scene);

    }

    final String HOVERED_BUTTON_STYLE = "-fx-background-color: #ff0800; -fx-background-radius: 20px; -fx-text-fill: white";
    final String IDLE_BUTTON_STYLE = "-fx-background-color: transparent; -fx-text-fill: #ff0800; -fx-border-color: #ff0800; -fx-border-radius: 20px";



    public void setStyleOnHover(Button btn){
        btn.setOnMouseEntered(e -> {btn.setStyle(HOVERED_BUTTON_STYLE);});
        btn.setOnMouseExited(e -> {btn.setStyle(IDLE_BUTTON_STYLE);});
    }

}
