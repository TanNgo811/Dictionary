package code;

import java.io.*;
import java.util.ArrayList;

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
        management.insertFromFile(newDictionary);
//        program.dictionaryAdvanced(newDictionary);
        management.dictionaryExportToFile(newDictionary, "export");
    }

}
