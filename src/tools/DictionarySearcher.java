package tools;

import code.Word;
import javafx.collections.transformation.FilteredList;

import java.util.ArrayList;

public class DictionarySearcher {
    public static ArrayList<Word> searcherForCommandline(String searchWord, ArrayList<Word> words) {
        if (searchWord == null || searchWord.equals("")) {
            return words;
        }

        ArrayList<Word> result = new ArrayList<>();

        for (Word word : words) {
            if (word.getWordTarget().toLowerCase().contains(searchWord)) {
                result.add(word);
            }
        }

        return result;
    }

    public static ArrayList<Word> searchBeginningList(String searchWord, ArrayList<Word> words) {
        if (searchWord == null || searchWord.equals("")) {
            return words;
        }

        ArrayList<Word> result = new ArrayList<>();
        String regex = "^" + searchWord.toLowerCase()+ ".*" ;
        for (Word word : words) {
            if (word.getWordTarget().toLowerCase().matches(regex)) {
                result.add(word);
            }
        }

        return result;
    }

    public static Word exactSearcherWord(String searchWord, ArrayList<Word> words) {
        for (Word i : words) {
            if (i.getWordTarget().toLowerCase().equals(searchWord)) {
                return i;
            }
        }
        return new Word();
    }

}
