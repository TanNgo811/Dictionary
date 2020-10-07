package application.controller;

import code.Dictionary;
import code.DictionaryManagement;
import code.Word;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class EditController implements Initializable{

    @FXML
    public Button btnCancel;

    @FXML
    public Button btnConfirm;

    @FXML
    public TextArea taEnglish;

    @FXML
    public TextArea taVietnamese;

    private Word oldWord;
    private DictionaryManagement management;
    private Dictionary dictionary;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        taEnglish.setText(oldWord.getWordTarget());
//        taVietnamese.setText(oldWord.getWordExplain());
    }

    public void EditController(DictionaryManagement management, Word oldWord) {
        this.management = management;
        this.oldWord = oldWord;
    }

    public void handleConfirmBtn(ActionEvent actionEvent) throws IOException {
        String wordTarget = taEnglish.getText();
        String wordExplain = taVietnamese.getText();

        if (wordTarget == null || wordExplain == null || wordTarget.equals("") || wordExplain.equals("")) {
            System.out.println("Edit Error!");
        } else {
            management.deleteWords(dictionary, oldWord.getWordTarget());
            management.addWords(dictionary, wordTarget, wordExplain);
            management.dictionaryExportToFile(dictionary, "dict2");

            goBack(actionEvent);
            System.out.println("Edit Successfully");

        }
    }

    public void goBack (ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../fxml/MainScene.fxml"));
        Parent mainParent = loader.load();
        Scene scene = new Scene(mainParent);
        stage.setScene(scene);
    }



}
