package code;

import java.io.*;
import java.util.*;

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
        Word newWord;
        try {
            File dictionaryFile = new File(".\\src\\dictionaries.txt");
            Scanner sc = new Scanner(dictionaryFile);

            while (sc.hasNextLine()){
                String line = sc.nextLine();
                String english = "";
                String vietnamese = "";
                String[] arrOfStr = line.split("\\|");
                english = arrOfStr[0];
                for (int i = 1; i < arrOfStr.length - 1; i++) {
                    vietnamese += arrOfStr[i] + '\n';
                }
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
     * Add Words Function.
     */
    public static void addAdvanceWords(Dictionary testDictionary, String english, String vietnamese, String pronounce, String type) {
        AdvancedWord newWord = new AdvancedWord(english, vietnamese,type,pronounce);
        testDictionary.words.add(newWord);
    }

    /**
     * Edit Words Function.
     */
    public static void editWords(Dictionary testDictionary, String english, String vietnamese) {
        for (Word i : testDictionary.words) {
            if (i.getWordTarget().equals(english)) {
                i.setWordExplain(vietnamese);
            }
        }
    }

    public static void editAdvanceWords(Dictionary testDictionary, String english, String vietnamese, String pronounce, String type) {
        for (Word i : testDictionary.words) {
            if (i.getWordTarget().equals(english)) {
                i.setWordExplain(vietnamese);
            }
        }
    }

    /**
     * Delete Words Function.
     */
    public static void deleteWords(Dictionary testDictionary, String english) {
        testDictionary.words.removeIf(n -> (n.getWordTarget().equals(english)));
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
            File fout = new File(".\\src\\" + file + ".txt");
            FileOutputStream fos = new FileOutputStream(fout);

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
            for (Word i : testDictionary.words) {
//                bw.write(i.getWordTarget() + "\t" + i.getWordExplain());
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

    public static void sortWords(Dictionary testDictionary) {
        testDictionary.words.sort(new Comparator<Word>() {
            @Override
            public int compare(Word word1, Word word2) {
                return word1.getWordTarget().compareTo(word2.getWordTarget());
            }
        });
    }
}
