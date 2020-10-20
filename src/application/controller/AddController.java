package application.controller;

import application.Main;
import code.DictionaryManagement;
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



public class AddController implements Initializable {

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

        if (wordTarget == null || wordExplain == null || wordTarget.equals("") || wordExplain.equals("")) {
            System.out.println("Add Error!");
            openAlertWindow("Add Error!");
        } else {
            DictionaryManagement.addWords(mainDictionary, wordTarget, wordExplain);
            DictionaryManagement.sortWords(mainDictionary);
            DictionaryManagement.dictionaryExportToFile(mainDictionary, "English-Vietnamese");

            goBack(actionEvent);
            System.out.println("Add Successfully!");
            openAlertWindow("Add " + wordTarget + " Successfully!");
        }
    }
}
