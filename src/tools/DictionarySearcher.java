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

        for (int i = 0; i < words.size(); i++) {
            if (words.get(i).getWordTarget().toLowerCase().contains(searchWord)) {
                result.add(words.get(i));
            }
        }

        return result;
    }

    public static Word searcherWord(String searchWord, ArrayList<Word> words) {
        ArrayList<Word> result = new ArrayList<>();

        for (int i = 0; i < words.size(); i++) {
            if (words.get(i).getWordTarget().toLowerCase().contains(searchWord)) {
                result.add(words.get(i));
            }
        }

        return result.get(0);
    }

}
