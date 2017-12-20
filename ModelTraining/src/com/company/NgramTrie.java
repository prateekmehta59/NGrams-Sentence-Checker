package com.company;

import java.io.*;
import java.util.*;

// Start string is assumed to be "1"
// End string is assumed to be "2"
public class NgramTrie {
    private TrieRoot wordDict;
    // bigram data Strutcture
    // Each two words are added to Trie with a space between them
    private TrieRoot prevWord2;
    // Each word is added to Trie similar to dictionary
    private TrieRoot prevWordCount2;

    // 3gram data Strutcture
    // Each three words are added to Trie with a space between them
    private TrieRoot prevWord3;
    // Each word is added to Trie similar to dictionary
    private TrieRoot prevWordCount3;

    // 4gram data Strutcture
    // Each four words are added to Trie with a space between them
    private TrieRoot prevWord4;
    // Each word is added to Trie similar to dictionary
    private TrieRoot prevWordCount4;

    public NgramTrie(String source) {
        FileInputStream fis;
        ObjectInputStream ois;
        try {
            fis = new FileInputStream( source + "wordDict.ser");
            ois = new ObjectInputStream(fis);
            wordDict = (TrieRoot) ois.readObject();

            fis = new FileInputStream( source + "prevWord2.ser");
            ois = new ObjectInputStream(fis);
            prevWord2 = (TrieRoot) ois.readObject();

            fis = new FileInputStream( source + "prevWordCount2.ser");
            ois = new ObjectInputStream(fis);
            prevWordCount2 = (TrieRoot) ois.readObject();

            fis = new FileInputStream( source + "prevWord3.ser");
            ois = new ObjectInputStream(fis);
            prevWord3 = (TrieRoot) ois.readObject();

            fis = new FileInputStream( source + "prevWordCount3.ser");
            ois = new ObjectInputStream(fis);
            prevWordCount3 = (TrieRoot) ois.readObject();

            fis = new FileInputStream( source + "prevWord4.ser");
            ois = new ObjectInputStream(fis);
            prevWord4 = (TrieRoot) ois.readObject();

            fis = new FileInputStream( source + "prevWordCount4.ser");
            ois = new ObjectInputStream(fis);
            prevWordCount4 = (TrieRoot) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void sample4gramsentence(String start){
        int max;
        int currentValue;
        System.out.println(start);
        String current = "xx " + start + " ";
        System.out.print(start + " ");
        String nextString = null;
        String temp;
        ArrayList<String> wordList;
        while(true){
            max = -1;
            wordList = prevWord4.getAllWordsPrefix(current);
            for(String word: wordList) {
                currentValue = this.prevWord4.getValue(current + word);
                if (currentValue > max) {
                    max = currentValue;
                    nextString = word;
                }
            }
            if(nextString.equals("yy"))
                break;
            System.out.print(nextString + " ");
            temp = current.split(" ")[1] + " " + current.split(" ")[2];
            temp += " " + nextString + " ";
            current = temp;
        }
        System.out.println();
    }

    public void sample3gramsentence(String start){
        int max;
        int currentValue;
        String current = "xx " + start + " ";
        System.out.print(start + " ");
        String nextString = null;
        String temp;
        ArrayList<String> wordList;
        while(true){
            max = -1;
            wordList = prevWord3.getAllWordsPrefix(current);
            for(String word: wordList) {
                currentValue = this.prevWord3.getValue(current + word);
                if (currentValue > max) {
                    max = currentValue;
                    nextString = word;
                }
            }
            if(nextString.equals("yy"))
                break;
            System.out.print(nextString + " ");
            temp = current.split(" ")[1];
            temp += " " + nextString + " ";
            current = temp;
        }
        System.out.println();
    }
}
