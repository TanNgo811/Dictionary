package application.controller;

import code.Dictionary;
import code.DictionaryManagement;
import code.Word;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javazoom.jl.decoder.JavaLayerException;
import tools.DictionarySearcher;
import tools.TextToSpeechGoogle;
import application.controller.EditController;

import static application.Main.mainDictionary;
import static application.controller.NotiWindow.openAlertWindow;
import static tools.Database.insertFromDatabase;

import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;

import javafx.event.ActionEvent;
import tools.Translator;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainController implements Initializable {

    @FXML
    public Button btnEdit;

    @FXML
    public Button btnAdd;

    @FXML
    public Button btnDelete;

    @FXML
    public Button btnExport;

    @FXML
    public Button btnSound;

    @FXML
    public Button btnGTranslate;

    @FXML
    public TextField tfSearchedWord;

    @FXML
    public ListView<String> lvWords;

    @FXML
    public TextArea taMeaning;

    @FXML
    public Label lbWord;

    @FXML
    public ImageView imgSearch;

    @FXML
    public ImageView imgSound;

    @FXML
    public Button btnExit;

//    Dictionary mainDictionary = new Dictionary();
//    DictionaryManagement management = new DictionaryManagement();

    public MainController(){

    }

    final String HOVERED_BUTTON_STYLE = "-fx-background-color: #858585";//-fx-outer-border, -fx-inner-border, -fx-body-color;
    final String IDLE_BUTTON_STYLE = "-fx-background-color: transparent;";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mainDictionary.words.clear();
        DictionaryManagement.insertFromFile(mainDictionary);
//        insertFromDatabase(mainDictionary);
        DictionaryManagement.sortWords(mainDictionary);
        this.updateWordList(mainDictionary.words);

        taMeaning.setWrapText(true);
        taMeaning.setEditable(false);

        tfSearchedWord.setOnKeyTyped(event -> {
            String searchedWord = tfSearchedWord.getText();
            ArrayList<Word> searchList = DictionarySearcher.searchBeginningList(searchedWord.toLowerCase(), mainDictionary.words);
            updateWordList(searchList);

        });

        imgSearch.setOnMouseClicked(event -> {
            String searchedWord = tfSearchedWord.getText();
            System.out.println("Searched Word: " + searchedWord);

            ArrayList<Word> searchList = DictionarySearcher.searchBeginningList(searchedWord.toLowerCase(), mainDictionary.words);
            updateWordList(searchList);
            try {
                lbWord.setText(searchList.get(0).getWordTarget());
                taMeaning.setText(searchList.get(0).getWordExplain());
            } catch (IndexOutOfBoundsException ibe) {
                GoogleTranslate(tfSearchedWord.getText());
            }

            if (searchedWord.equals("")) {
                lbWord.setText("");
                taMeaning.clear();
            }
        });

        lvWords.setOnMouseClicked(event -> {
            String searchedWord = lvWords.getSelectionModel().getSelectedItem();
            try {
                if (!searchedWord.equals("")||searchedWord != null) {
                    System.out.println("Clicked Word: " + searchedWord);
                    tfSearchedWord.setText(searchedWord);
                    Word clickedWord = DictionarySearcher.exactSearcherWord(searchedWord, mainDictionary.words);
                    lbWord.setText(searchedWord);
                    taMeaning.setText(clickedWord.getWordExplain());
                }
            } catch (NullPointerException e) {
                    System.out.println("Nothing Here!!!");
            }

        });

        btnDelete.setOnMouseClicked(event ->{
            String word = lbWord.getText();
            if (!word.equals("")) {
                DictionaryManagement.deleteWords(mainDictionary, word);
                System.out.println("Delete Worked!!!");
                openAlertWindow("Delete \"" + word + " \"Successfully!");
                updateWordList(mainDictionary.words);
                lbWord.setText("");
                taMeaning.clear();
            } else {
                System.out.println("Nothing to Delete!!!");
                openAlertWindow("Nothing to Delete!!!");
            }
            DictionaryManagement.dictionaryExportToFile(mainDictionary, "dict2");
        });

        btnGTranslate.setOnMouseClicked(event -> {
            GoogleTranslate(lbWord.getText());
        });

        setStyleOnHover(btnAdd);
        setStyleOnHover(btnDelete);
        setStyleOnHover(btnExport);
        setStyleOnHover(btnEdit);
        setStyleOnHover(btnExit);

        btnExit.setOnMouseClicked(event -> {
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.close();
        });
    }


    public void updateWordList(ArrayList<Word> updatedArray) {
        lvWords.getItems().clear();
        for (Word i : updatedArray){
            lvWords.getItems().add(i.getWordTarget());
        }
    }

    public Word getWordEdit() {
        return new Word(lbWord.getText(), taMeaning.getText());
    }

    public void GoogleTranslate(String text) {
        String wordExplain;
        try {
            lbWord.setText(text);
            wordExplain = Translator.translate("en", "vi", text);
            taMeaning.setText(wordExplain + "\nSearch using Google Translate...." );
            System.out.println("Search using Google Translate....");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleSearch(ActionEvent actionEvent) {
        String searchedWord = tfSearchedWord.getText();
        System.out.println("Searched Word: " + searchedWord);

        ArrayList<Word> searchList = DictionarySearcher.searchBeginningList(searchedWord.toLowerCase(), mainDictionary.words);
        updateWordList(searchList);
        try {
            lbWord.setText(searchList.get(0).getWordTarget());
            taMeaning.setText(searchList.get(0).getWordExplain());
        } catch (IndexOutOfBoundsException ibe) {
            GoogleTranslate(tfSearchedWord.getText());
        }

        if (searchedWord.equals("")) {
            lbWord.setText("");
            taMeaning.clear();
        }
    }

    public void handleEdit(ActionEvent actionEvent) throws IOException{
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../fxml/EditWord.fxml"));
        Parent editViewParent = loader.load();
//        editViewParent = FXMLLoader.load(getClass().getResource("EditWord.fxml"));
        EditController editController = loader.getController();
        editController.setWordEdit(getWordEdit());

        Scene scene = new Scene(editViewParent);
//        EditController controller = new EditController();
//        String selected = getLabel();
//        controller.setWordEdit(selected);
        stage.setScene(scene);
    }

    public void handleAdd(ActionEvent actionEvent) throws IOException{
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../fxml/AddWord.fxml"));
        Parent editViewParent = loader.load();
//        Parent editViewParent = FXMLLoader.load(getClass().getResource("EditWord.fxml"));
        Scene scene = new Scene(editViewParent);
        stage.setScene(scene);
    }

    public void handleExport(ActionEvent actionEvent) throws IOException{
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../fxml/ExportFile.fxml"));
        Parent editViewParent = loader.load();
//        Parent editViewParent = FXMLLoader.load(getClass().getResource("EditWord.fxml"));
        Scene scene = new Scene(editViewParent);
        stage.setScene(scene);
    }

    public void handleTextToSpeed(ActionEvent actionEvent)
    {
        System.out.println("Playing Text to Speech");
        String word =lbWord.getText();

        btnSound = (Button) actionEvent.getSource();
        String targetLang = btnSound.getId();

        Thread playAudioThread = new Thread(() -> PLayTextToSpeechAudio(word, targetLang));
        playAudioThread.start();

    }

    public void setStyleOnHover(Button btn){
        btn.setOnMouseEntered(e -> {btn.setStyle(HOVERED_BUTTON_STYLE);});
        btn.setOnMouseExited(e -> {btn.setStyle(IDLE_BUTTON_STYLE);});
    }

    public void PLayTextToSpeechAudio(String word, String language) {
        TextToSpeechGoogle audio = new TextToSpeechGoogle();
        InputStream sound = null;

        if (word == null) {
            word = lbWord.getText();
        }

        try {
            sound = audio.getAudio(word, language);
        } catch (IOException ioe) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ioe);
        }

        try {
            if (sound != null) {
                audio.play(sound);
            }
        } catch (JavaLayerException jle) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, jle);
        }
    }

}
