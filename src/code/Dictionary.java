package code;

import java.io.*;
import java.util.ArrayList;
import tools.DictionarySearcher;

public class Dictionary {
    public ArrayList<Word>words = new ArrayList<Word>();

    /**
     * Ham main.
     * @param args
     */
    public static void main(String[] args) {
        Dictionary newDictionary = new Dictionary();
        DictionaryCommandline program = new DictionaryCommandline();
        DictionaryManagement management = new DictionaryManagement();
        DictionarySearcher searcher = new DictionarySearcher();

        management.insertFromFile(newDictionary);
//        program.dictionaryAdvanced(newDictionary);
//        management.dictionaryExportToFile(newDictionary, "export");
        ArrayList<Word> newWords = searcher.searcherForCommandline("ear", newDictionary.words);

        for (Word i : newWords) {
            System.out.println(i.getWordTarget());
        }

    }

}
