package application.controller_with_db;

import code.DictionaryManagement;
import code.Word;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static application.Main.mainDictionary;
import static application.controller.NotiWindow.openAlertWindow;
import static tools.Database.insertFromDatabase;
import static tools.Database.addIntoDatabase;

public class AddController_db implements Initializable {

    @FXML
    public Button btnCancel;

    @FXML
    public Button btnConfirm;

    @FXML
    public TextArea taEnglish;

    @FXML
    public TextArea taVietnamese;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setStyleOnHover(btnConfirm);
        setStyleOnHover(btnCancel);
    }

    public void goBack (ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../fxml/MainScene.fxml"));
        Parent mainParent = loader.load();
        Scene scene = new Scene(mainParent);
        stage.setScene(scene);
    }

    public void handleBtnConfirm (ActionEvent actionEvent) throws IOException {
        String wordTarget = taEnglish.getText();
        String wordExplain = taVietnamese.getText();
        Word newWord = new Word(wordTarget, wordExplain);
        if (wordTarget == null || wordExplain == null || wordTarget.equals("") || wordExplain.equals("")) {
            System.out.println("Add Error!");
            openAlertWindow("Add Error!");
        } else {
//            DictionaryManagement.addWords(mainDictionary, wordTarget, wordExplain);
//            DictionaryManagement.sortWords(mainDictionary);
//            DictionaryManagement.dictionaryExportToFile(mainDictionary, "English-Vietnamese");
            addIntoDatabase(newWord);
            goBack(actionEvent);
            System.out.println("Add Successfully!");
            openAlertWindow("Add " + wordTarget + " Successfully!");
        }
    }

    final String HOVERED_BUTTON_STYLE = "-fx-background-color: #ff0800; -fx-background-radius: 20px; -fx-text-fill: white";
    final String IDLE_BUTTON_STYLE = "-fx-background-color: transparent; -fx-text-fill: #ff0800; -fx-border-color: #ff0800; -fx-border-radius: 20px";



    public void setStyleOnHover(Button btn){
        btn.setOnMouseEntered(e -> {btn.setStyle(HOVERED_BUTTON_STYLE);});
        btn.setOnMouseExited(e -> {btn.setStyle(IDLE_BUTTON_STYLE);});
    }




}
