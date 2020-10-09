package application.controller;

import code.Dictionary;
import code.DictionaryManagement;
import code.Word;
import javazoom.jl.decoder.JavaLayerException;
import tools.DictionarySearcher;
import tools.TextToSpeechGoogle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainController implements Initializable {
    @FXML
    public Button btSearch;

    @FXML
    public Button btnEdit;

    @FXML
    public Button btnAdd;

    @FXML
    public Button btnDelete;

//    @FXML
//    public Button btnSound;

    @FXML
    public TextField tfSearchedWord;

    @FXML
    public ListView<String> lvWords;

    @FXML
    public TextArea taMeaning;

    Dictionary dictionary = new Dictionary();
//    DictionaryManagement management = new DictionaryManagement();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.initializeWordList();

        btSearch.setOnMouseClicked(event -> {
            System.out.println("Search!!!");
            String searchedWord = tfSearchedWord.getText();
            if (!searchedWord.trim().equals("")) {
                System.out.println("Searched Word: " + searchedWord);
                ArrayList<Word> searchList = DictionarySearcher.searcherForCommandline(searchedWord, dictionary.words);
                String wordMeaning = "";
                try {
                    updateWordList(searchList);
                    wordMeaning = searchList.get(0).getWordExplain();
                } catch  (IndexOutOfBoundsException iobe) { //(NullPointerException npe)

                }
                taMeaning.setText(wordMeaning);
            } else {
                System.out.println("Reset!!!");
                initializeWordList();
            }
        });

        lvWords.setOnMouseClicked(event -> {
            String searchedWord = lvWords.getSelectionModel().getSelectedItem();
            if (!searchedWord.equals("")) {
                System.out.println("Clicked Word: " + searchedWord);
                String wordMeaning = new String();
                for (Word i : dictionary.words){
                    try {
                        if (i.getWordTarget().equals(searchedWord)) {
                            wordMeaning = i.getWordExplain();
                            tfSearchedWord.setText(searchedWord);
                        }
                    } catch (NullPointerException npe){

                    }
                }
                taMeaning.setText(wordMeaning);
            }

        });

//        btnEdit.setOnMouseClicked(event -> {
//            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
//            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation(getClass().getResource("EditWord.fxml"));
//            Parent editViewParent = null;
//            try {
//                editViewParent = loader.load();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
////        Parent editViewParent = FXMLLoader.load(getClass().getResource("EditWord.fxml"));
//            Scene scene = new Scene(editViewParent);
//            stage.setScene(scene);
//        });

        btnDelete.setOnMouseClicked(event ->{
            String word = tfSearchedWord.getText();
            if (!word.equals("")) {
                DictionaryManagement.deleteWords(dictionary, word);
                //TODO: make notification
                System.out.println("Delete Worked!!!");
                updateWordList(dictionary.words);
            } else {
                System.out.println("Nothing to Delete!!!");
            }

            //TODO: add export after delete word
        });
    }

    public void initializeWordList() {
        dictionary.words.clear();
        DictionaryManagement.insertFromFile(dictionary);
        lvWords.getItems().clear();
        for (Word i : dictionary.words){
            lvWords.getItems().add(i.getWordTarget());
        }
    }

    public void updateWordList(ArrayList<Word> updatedArray) {
        lvWords.getItems().clear();
        for (Word i : updatedArray){
            lvWords.getItems().add(i.getWordTarget());
        }
    }

    public void handleEdit(ActionEvent actionEvent) throws IOException{
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../fxml/EditWord.fxml"));
        Parent editViewParent = loader.load();
//        Parent editViewParent = FXMLLoader.load(getClass().getResource("EditWord.fxml"));
        Scene scene = new Scene(editViewParent);
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

    public void handleDelete(ActionEvent actionEvent) throws IOException{

    }

    public void handleTextToSpeed(ActionEvent actionEvent)
    {
        System.out.println(".......Playing");
        String word = tfSearchedWord.getText();

        Button speaker = (Button) actionEvent.getSource();
        String targetLang = speaker.getId();

        Thread playAudioThread = new Thread(() -> {
            PLayTextToSpeechAudio(word, targetLang);
        });
        playAudioThread.start();

    }

    public void PLayTextToSpeechAudio(String word, String language) {
        TextToSpeechGoogle audio = new TextToSpeechGoogle();
        InputStream sound = null;

        if (word == null) {
            word = tfSearchedWord.getText();
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
