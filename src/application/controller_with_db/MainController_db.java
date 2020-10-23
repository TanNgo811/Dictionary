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
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;
import javazoom.jl.decoder.JavaLayerException;
import tools.DictionarySearcher;
import tools.TextToSpeechGoogle;
import tools.Translator;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static application.Main.mainDictionary;
import static application.Main.searchedWords;
import static application.controller.NotiWindow.openAlertWindow;
import static tools.Database.deleteFromDatabase;
import static tools.Database.insertFromDatabase;

public class MainController_db implements Initializable {

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
    public Button btnExit;

    @FXML
    public Button us;

    @FXML
    public Button uk;

    @FXML
    public SVGPath GoogleSVG;

    @FXML
    public Button btnMinimize;

    @FXML
    public Rectangle recMain;

    @FXML
    public Button btnAbout;

//    Dictionary mainDictionary = new Dictionary();
//    DictionaryManagement management = new DictionaryManagement();

    public MainController_db() {

    }

    final String HOVERED_BUTTON_STYLE = "-fx-background-color: #858585";
    final String IDLE_BUTTON_STYLE = "-fx-background-color: transparent";

    final String HOVERED_BUTTON_STYLE2 = "-fx-background-color: #ff0800; -fx-background-radius: 10px; -fx-text-fill: white";
    final String IDLE_BUTTON_STYLE2 = "-fx-background-color: transparent; -fx-text-fill: #ff0800; -fx-border-color: #ff0800; -fx-border-radius: 10px";
    double x, y;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lbWord.setText("OpenSource E-V Dictionary");
        taMeaning.setText("Main developer: \nNGO TIEN TAN - 18021123\n & LE TIEN PHAT - 18020993 \nWord & translation source from http://informatik.uni-lebzig.de/~duc/Dict & Google Translate Source");

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
                searchedWords.add(searchList.get(0));
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
                if (!searchedWord.equals("") || searchedWord != null) {
                    System.out.println("Clicked Word: " + searchedWord);
                    tfSearchedWord.setText(searchedWord);
                    Word clickedWord = DictionarySearcher.exactSearcherWord(searchedWord, mainDictionary.words);
                    lbWord.setText(searchedWord);
                    taMeaning.setText(clickedWord.getWordExplain());
                    searchedWords.add(clickedWord);
                }
            } catch (NullPointerException e) {
                System.out.println("Nothing Here!!!");
            }

        });

        btnDelete.setOnMouseClicked(event -> {
            String word = lbWord.getText();
            if (!word.equals("")) {
                DictionaryManagement.deleteWords(mainDictionary, word);
                deleteFromDatabase(word);
                System.out.println("Delete Worked!!!");
                openAlertWindow("Delete \"" + word + " \"Successfully!");
                updateWordList(mainDictionary.words);
                lbWord.setText("");
                taMeaning.clear();
            } else {
                System.out.println("Nothing to Delete!!!");
                openAlertWindow("Nothing to Delete!!!");
            }
//            DictionaryManagement.dictionaryExportToFile(mainDictionary, "dict2");
        });

        btnGTranslate.setOnMouseClicked(event -> {
            GoogleTranslate(lbWord.getText());
        });

        setStyleOnHover(btnAdd, HOVERED_BUTTON_STYLE, IDLE_BUTTON_STYLE);
        setStyleOnHover(btnDelete, HOVERED_BUTTON_STYLE, IDLE_BUTTON_STYLE);
        setStyleOnHover(btnExport, HOVERED_BUTTON_STYLE, IDLE_BUTTON_STYLE);
        setStyleOnHover(btnEdit, HOVERED_BUTTON_STYLE, IDLE_BUTTON_STYLE);
        setStyleOnHover(btnExit, HOVERED_BUTTON_STYLE, IDLE_BUTTON_STYLE);
        setStyleOnHover(btnMinimize, HOVERED_BUTTON_STYLE, IDLE_BUTTON_STYLE);

//        setStyleOnHover(us, HOVERED_BUTTON_STYLE2, IDLE_BUTTON_STYLE2);
//        setStyleOnHover(uk, HOVERED_BUTTON_STYLE2, IDLE_BUTTON_STYLE2);
//        setStyleOnHover(btnGTranslate, HOVERED_BUTTON_STYLE2, IDLE_BUTTON_STYLE2);
//
//        GoogleSVG.setOnMouseEntered(e -> {GoogleSVG.setStyle("-fx-fill: white");});
//        GoogleSVG.setOnMouseExited(e -> {GoogleSVG.setStyle("-fx-fill: #ff0800");});
        btnAbout.setOnMouseClicked(event -> {
            lbWord.setText("OpenSource E-V Dictionary");
            taMeaning.setText("Main developer: \nNGO TIEN TAN - 18021123\n & LE TIEN PHAT - 18020993 \nWord & translation source from http://informatik.uni-lebzig.de/~duc/Dict & Google Translate Source");
        });

        btnMinimize.setOnMouseClicked(event -> {
            ((Stage) ((Button) event.getSource()).getScene().getWindow()).setIconified(true);
        });

        btnExit.setOnMouseClicked(event -> {
            ((Stage) ((Button) event.getSource()).getScene().getWindow()).close();
        });

        recMain.setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });

        recMain.setOnMouseDragged(event -> {
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        });
    }


    public void updateWordList(ArrayList<Word> updatedArray) {
        lvWords.getItems().clear();
        for (Word i : updatedArray) {
            lvWords.getItems().add(i.getWordTarget());
        }
    }

    public Word getWordEdit() {
        return new Word(lbWord.getText(), taMeaning.getText());
    }

    public ArrayList<Word> getSearchWords() {
        return searchedWords;
    }

    public void GoogleTranslate(String text) {
        String wordExplain;
        try {
            lbWord.setText(text);
            wordExplain = Translator.translate("en", "vi", text);
            searchedWords.add(new Word(text, wordExplain));
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
        EditController_db editController = loader.getController();
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
        if (searchedWords.isEmpty()) {
            openAlertWindow("Please search some words to export!");
        } else {
            Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../fxml/ExportFile.fxml"));
            Parent editViewParent = loader.load();
//            ExportController exportController = loader.getController();
//            exportController.setLVSearchWords(getSearchWords());
//        Parent editViewParent = FXMLLoader.load(getClass().getResource("EditWord.fxml"));
            Scene scene = new Scene(editViewParent);
            stage.setScene(scene);
        }
    }

    public void handleTextToSpeed(ActionEvent actionEvent) {
        System.out.println("Playing Text to Speech");
        String word = lbWord.getText();

        btnSound = (Button) actionEvent.getSource();
        String targetLang = btnSound.getId();

        Thread playAudioThread = new Thread(() -> PLayTextToSpeechAudio(word, targetLang));
        playAudioThread.start();

    }

    public void setStyleOnHover(Button btn, String hover, String idle){
        btn.setOnMouseEntered(e -> {btn.setStyle(hover);});
        btn.setOnMouseExited(e -> {btn.setStyle(idle);});
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
            Logger.getLogger(MainController_db.class.getName()).log(Level.SEVERE, null, ioe);
        }

        try {
            if (sound != null) {
                audio.play(sound);
            }
        } catch (JavaLayerException jle) {
            Logger.getLogger(MainController_db.class.getName()).log(Level.SEVERE, null, jle);
        }
    }
}
