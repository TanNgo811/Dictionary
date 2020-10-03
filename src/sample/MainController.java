package sample;

import code.Dictionary;
import code.DictionaryManagement;
import code.Word;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    public Button btSearch;

    @FXML
    public TextField tfSearchedWord;

    @FXML
    public ListView<String> lvWords;

    @FXML
    public TextArea taMeaning;

    Dictionary dictionary = new Dictionary();
    DictionaryManagement management = new DictionaryManagement();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btSearch.setOnMouseClicked(event -> {
            System.out.println("Search!!!");
            String searchedWord = tfSearchedWord.getText();
            if (searchedWord != null && searchedWord.equals("") == false) {
                System.out.println("Searched Word: " + searchedWord);
                String wordMeaning = new String();
                for (Word i : dictionary.words){
                    try {
                        if (i.getWordTarget().equals(searchedWord)) {
                            wordMeaning = i.getWordExplain();

                        }
                    } catch (NullPointerException npe){

                    }

                }
                taMeaning.setText(wordMeaning);
            }
        });
        this.initializeWordList();

        lvWords.setOnMouseClicked(event -> {
            String searchedWord = lvWords.getSelectionModel().getSelectedItem();
            if (searchedWord != null && searchedWord.equals("") == false) {
                System.out.println("Searched World: " + searchedWord);
                String wordMeaning = new String();
                for (Word i : dictionary.words){
                    try {
                        if (i.getWordTarget().equals(searchedWord)) {
                            wordMeaning = i.getWordExplain();

                        }
                    } catch (NullPointerException npe){

                    }
                }
                taMeaning.setText(wordMeaning);
            }

        });


    }

    public void initializeWordList() {
        management.insertFromFile(dictionary);
        for (Word i : dictionary.words){
            lvWords.getItems().add(i.getWordTarget());
            System.out.println(i.getWordTarget());
        }
    }




}
