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

//        management.insertFromFile(newDictionary);
//        program.dictionaryAdvanced(newDictionary);
//        management.dictionaryExportToFile(newDictionary, "export");
//        ArrayList<Word> newWords = searcher.searcherForCommandline("ear", newDictionary.words);

//        for (Word i : newWords) {
//            System.out.println(i.getWordTarget());
//        }

//        String line = "4th|Thứ 4|ADJ;thứ tư;|wednesday, fourth, wed, 4th;";
        /*String eng = "", vie = "";
        int i = 0;
        for (i = 0; line.charAt(i) != '|'; i++) {
            eng = eng + line.charAt(i);
        }
        int index = i + 1;
        for (i = index; line.charAt(i) != '|'; i++) {
            vie = vie + line.charAt(i);
        }
        System.out.println(eng);
        System.out.println(vie);*/
        /*String[] arrOfStr = line.split("\\s*[a-zA-Z]+|");
        String english = "", vietnamese = "";
        english = arrOfStr[0];
        vietnamese = arrOfStr[1];
        System.out.println(english);
        System.out.println(vietnamese);*/

    }

}
