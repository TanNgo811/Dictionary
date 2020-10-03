package code;

import java.io.*;
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
    public static void editWords(Dictionary testDictionary, String english, String vietnamese) {
        for (Word i : testDictionary.words) {
            if (i.getWordTarget() == english) {
                i.setWordExplain(vietnamese);
            }
        }
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
     * Create File Function.
     */
    public static String dictionaryCreateFile(Dictionary testDictionary, String filename) {
        String file = filename + ".txt";
        try {
            File expFile = new File(file);
            if (expFile.createNewFile()) {
                System.out.println("File created: " + expFile.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return file;
    }

    /**
     * Export To File Function.
     */
    public static void dictionaryExportToFile(Dictionary testDictionary, String file) {
        try {
            File fout = new File(file + ".txt");
            FileOutputStream fos = new FileOutputStream(fout);

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
            for (Word i : testDictionary.words) {
                bw.write(i.getWordTarget() + "\t" + i.getWordExplain());
                bw.newLine();
            }
//            fileWriter.write("Files in Java might be tricky, but it is fun enough!");
//            fileWriter.write(newDictionary.words.get(1).getWordTarget() + " " + newDictionary.words.get(1).getWordExplain());
            bw.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void dictionarySearcher(Dictionary testDictionary) {

    }




}
