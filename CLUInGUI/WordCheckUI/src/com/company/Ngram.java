package com.company;

import java.io.*;
import java.util.*;

// Start string is assumed to be "1"
// End string is assumed to be "2"
public class Ngram {
    private HashSet<String> wordDict;
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
    private HashMap<String, HashMap<String, Integer>> prevWord4;
    // Maps each word to number of its occurrence
    // private HashMap<String, Integer> prevWordCount4;

    public Ngram(String source) {
        FileInputStream fis;
        ObjectInputStream ois;
        try {
            fis = new FileInputStream( source + "wordDict.ser");
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
            fis = new FileInputStream( source + "prevWord4.ser");
            ois = new ObjectInputStream(fis);
            prevWord4 = (HashMap<String, HashMap<String, Integer>>) ois.readObject();
            /*
            fis = new FileInputStream( source + "prevWordCount4.ser");
            ois = new ObjectInputStream(fis);
            prevWordCount4 = (HashMap<String, Integer>) ois.readObject();
            */
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public HashSet<Integer> highlightWrongWords(ArrayList<String> input){
        // I is not in the word dict!!!!!!
        // Input example: 1 1 1 There is an apple 2
        HashSet<Integer> indexHashSet = new HashSet<>();
        if (input == null || input.size() < 4)
            return indexHashSet;
        for(int i = 0; i < input.size(); i++) {
            if ((input.get(i).equals("1")))
                continue;
            if(!(wordDict.contains(input.get(i).toLowerCase())))
                indexHashSet.add(i);
        }
        // System.out.println(indexHashSet);
        // System.out.println(input);
        String current;
        for(int i = 3; i < input.size(); i++){
            // If end of a sentence
            if(input.get(i).equals("2")) {
                i = i + 2;
                if(i > input.size() - 1)
                    break;
            }
            // if the words are valid
            if(!(indexHashSet.contains(i) || indexHashSet.contains(i - 1)
                    || indexHashSet.contains(i - 2) || indexHashSet.contains(i - 3))){
                current = input.get(i - 3) + " " + input.get(i - 2) + " " + input.get(i - 1);
                // System.out.println(current);
                // If current is a valid key
                if(prevWord4.containsKey(current)){
                    if(!prevWord4.get(current).containsKey(input.get(i)))
                        indexHashSet.add(i);
                }
                // If not add all to indexList
                else{
                    indexHashSet.add(i - 1);
                    indexHashSet.add(i - 2);
                    indexHashSet.add(i - 3);
                }
            }
        }
        /*
        Iterator iterator = indexHashSet.iterator();
        while (iterator.hasNext()){
            System.out.print(input.get((Integer) iterator.next()) + " ");
        }
        */
        // System.out.println();
        return indexHashSet;
    }
}
