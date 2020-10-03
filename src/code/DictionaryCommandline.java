package code;

import java.util.Scanner;

public class DictionaryCommandline {

    /**
     * Print All Words Function
     */
    public static void showAllWords(Dictionary testDictionary) {
        System.out.printf("%-8s|%-20s|%-15s%n", "NO", "English", "Vietnamese");
        int count = 1;
        for (Word i : testDictionary.words)
            try {
                System.out.printf("%-8s|%-20s|%-15s%n", count, i.getWordTarget(), i.getWordExplain());
                count++;
            } catch (NullPointerException npe) {

            }
    }

    /**
     * Basic Function of Dictionary
     */
    public static void dictionaryBasic(Dictionary testDictionary) {
        DictionaryManagement.insertFromCommandline(testDictionary);
        DictionaryCommandline.showAllWords(testDictionary);
    }

    /**
     * Advanced Function of Dictionary.
     */
    public static void dictionaryAdvanced(Dictionary testDictionary) {
        DictionaryManagement.insertFromFile(testDictionary);
        DictionaryCommandline.showAllWords(testDictionary);
//        DictionaryManagement.dictionaryLookup(testDictionary);
        Scanner sc = new Scanner(System.in);
        int choice;
        String searchWord = new String();

        do {
            System.out.println("Translate E-V(1) or V-E(2) or exit(0)? ");
            choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 0:
                    System.out.print("Thank you!");
                    break;

                case 1:
                    System.out.print("Enter Eng Word: ");
                    searchWord = sc.nextLine();
                    System.out.println(DictionaryManagement.EVLookup(testDictionary, searchWord));
                    break;

                case 2:
                    System.out.print("Enter Vi Word: ");
                    searchWord = sc.nextLine();
                    System.out.println(DictionaryManagement.VELookup(testDictionary, searchWord));
                    break;

                default:
                    System.out.println("Please re-enter your choice!");
                    break;
            }
        } while (choice != 0);
    }

    /**
     * Dictionary Searcher Function.
     */
    public static void dictionarySearcher(Dictionary testDictionary) {


    }
}
