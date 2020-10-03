package code;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DictionaryManagement {

    /**
     * insertFromCommandLine Function
     */
    public static void insertFromCommandline(Dictionary testDictionary) {
        Scanner sc = new Scanner(System.in);
        String english = new String();
        String vietnamese = new String();
        int sum = sc.nextInt();
        sc.nextLine();
        Word newWord;

        for (int i = 0; i < sum; i++) {
            newWord = new Word();
            english = sc.nextLine();
            vietnamese = sc.nextLine();

            try {
                newWord.setWordTarget(english);
                newWord.setWordExplain(vietnamese);
                testDictionary.words.add(newWord);
            } catch (NullPointerException npe) {

            }

        }
    }

    /**
     * insertFromFile Function.
     */
    public static void insertFromFile(Dictionary testDictionary) {
        String english = new String();
        String vietnamese = new String();
        Word newWord;
        try {
            File dictionaryFile = new File(".\\src\\dictionary.txt");
            Scanner sc = new Scanner(dictionaryFile);

            while (sc.hasNextLine()){
                String line = sc.nextLine();
                String[] arrOfStr = line.split("    ", 2);
                english = arrOfStr[0];
                vietnamese = arrOfStr[1];
                newWord = new Word();
                try{
                    newWord.setWordTarget(english);
                    newWord.setWordExplain(vietnamese);
                    testDictionary.words.add(newWord);
                } catch (NullPointerException npe){

                }
            }
        } catch (FileNotFoundException e){
            System.out.println("File Error!");
            e.printStackTrace();
        }

    }

    /**
     * E-V Look up Function.
     */
    public static String EVLookup (Dictionary testDictionary, String searchWord) {
        String result = new String();
        for (Word i : testDictionary.words){
            try {
                if (i.getWordTarget().equals(searchWord)) {
                    result = i.getWordExplain();
                }
            } catch (NullPointerException npe){

            }

        }
        return result;
    }

    /**
     * V-E Look up Function.
     */
    public static String VELookup (Dictionary testDictionary, String searchWord) {
        String result = new String();
        for (Word i : testDictionary.words) {
            try {
                if (i.getWordExplain().equals(searchWord)) {
                    result = i.getWordTarget();
                }
            } catch (NullPointerException npe){

            }
        }
        return result;
    }

    /**
     * Add Words Function.
     */
    public static void addWords(Dictionary testDictionary, String english, String vietnamese) {
        Word newWord = new Word(english, vietnamese);
        testDictionary.words.add(newWord);
    }

    /**
     * Edit Words Function.
     */
    public static void editWords(Dictionary testDictionary, String english) {

    }

    /**
     * Delete Words Function.
     */
    public static void deleteWords(Dictionary testDictionary, String english) {
        for (Word i : testDictionary.words) {
            if (i.getWordTarget().equals(english)){
                testDictionary.words.remove(i);
            }
        }
    }

    /**
     * Export To File Function.
     */
    public static void dictionaryExportToFile(Dictionary testDictionary) {


    }

    public static void dictionarySearcher(Dictionary testDictionary) {

    }




}
