package com.company;

import java.io.*;
import java.util.*;

// Start string is assumed to be "1"
// End string is assumed to be "2"
public class Ngram {
    public HashSet<String> wordDict = new HashSet<>();
    // bigram data Strutcture
    // Maps to each word to a Hashmap that is a possible next word and number of occurrence
    private HashMap<String, HashMap<String, Integer>> prevWord2;
    // Maps each word to number of its occurrence
    // private HashMap<String, Integer> prevWordCount2;

    // 3gram data Strutcture
    // Maps to each 2 words to a Hashmap that is a possible next word and number of occurrence
    private HashMap<String, HashMap<String, Integer>> prevWord3;
    // Maps each word to number of its occurrence
    // private HashMap<String, Integer> prevWordCount3;

    // 4gram data Strutcture
    // Maps to each 3 words to a Hashmap that is a possible next word and number of occurrence
    public HashMap<String, HashMap<String, Integer>> prevWord4 = new HashMap<>(1);
    // Maps each word to number of its occurrence
    // private HashMap<String, Integer> prevWordCount4;

    public Ngram(String source) {
        FileInputStream fis;
        ObjectInputStream ois;

        if (!new File(source + "wordDict.ser").exists() || !new File(source + "prevWord4.ser").exists())
            System.out.println("Serialzied Resource not imported");
        else {
            try {
                fis = new FileInputStream(source + "wordDict.ser");
                ois = new ObjectInputStream(fis);
                wordDict = (HashSet<String>) ois.readObject();
            /*
            fis = new FileInputStream( source + "prevWord2.ser");
            ois = new ObjectInputStream(fis);
            prevWord2 = (HashMap<String, HashMap<String, Integer>>) ois.readObject();
            */
            /*
            fis = new FileInputStream( source + "prevWordCount2.ser");
            ois = new ObjectInputStream(fis);
            prevWordCount2 = (HashMap<String, Integer>) ois.readObject();
            */
            /*
            fis = new FileInputStream( source + "prevWord3.ser");
            ois = new ObjectInputStream(fis);
            prevWord3 = (HashMap<String, HashMap<String, Integer>>) ois.readObject();
            */
            /*
            fis = new FileInputStream( source + "prevWordCount3.ser");
            ois = new ObjectInputStream(fis);
            prevWordCount3 = (HashMap<String, Integer>) ois.readObject();
            */
                fis = new FileInputStream(source + "prevWord4.ser");
                ois = new ObjectInputStream(fis);
                prevWord4 = (HashMap<String, HashMap<String, Integer>>) ois.readObject();
            /*
            fis = new FileInputStream( source + "prevWordCount4.ser");
            ois = new ObjectInputStream(fis);
            prevWordCount4 = (HashMap<String, Integer>) ois.readObject();
            */
                System.out.println("Serialized File Loaded ------ Success");
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                System.out.println("Serialized File no loaded");
            }
        }
    }
}
