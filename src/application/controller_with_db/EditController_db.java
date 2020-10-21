package application.controller_with_db;

import application.Main;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static application.controller.NotiWindow.openAlertWindow;
import static tools.Database.addIntoDatabase;
import static tools.Database.deleteFromDatabase;


public class EditController_db implements Initializable{

    @FXML
    public Button btnCancel;

    @FXML
    public Button btnConfirm;

    @FXML
    public TextArea taEnglish;

    @FXML
    public TextArea taVietnamese;

    @FXML
    public Label oldWord;

    MainController_db application = new MainController_db();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setStyleOnHover(btnCancel);
        setStyleOnHover(btnConfirm);
    }

    public EditController_db() {

    }

    public void setWordEdit(Word edit) {
        taEnglish.setText(edit.getWordTarget());
        taVietnamese.setText(edit.getWordExplain());
        oldWord.setText(edit.getWordTarget());
    }

    public void handleConfirmBtn(ActionEvent actionEvent) throws IOException {

        String wordTarget = taEnglish.getText();
        String wordExplain = taVietnamese.getText();
        Word newWord = new Word(wordTarget, wordExplain);

        if (wordTarget == null || wordExplain == null || wordTarget.equals("") || wordExplain.equals("")) {
            System.out.println("Edit Error!");
            openAlertWindow("Edit Error!");
        } else {
//            DictionaryManagement.deleteWords(Main.mainDictionary, oldWord.getText());
//            DictionaryManagement.addWords(Main.mainDictionary, wordTarget.toLowerCase(), wordExplain);
//            DictionaryManagement.sortWords(Main.mainDictionary);
            deleteFromDatabase(oldWord.getText());
            addIntoDatabase(newWord);
            System.out.println("Edit Successfully!");
            openAlertWindow("Edit " + oldWord.getText() + " Successfully!");
//            DictionaryManagement.dictionaryExportToFile(Main.mainDictionary, "English-Vietnamese");
            goBack(actionEvent);
        }
    }

    public void goBack(ActionEvent actionEvent) throws IOException {
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
