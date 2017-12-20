package com.company;

import java.awt.peer.WindowPeer;
import java.io.*;
import java.util.*;

// Start string is assumed to be "xx"
// End string is assumed to be "yy"
public class ProcessTextTrie {
    // dest for serialization
    private String dest;
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


    public ProcessTextTrie(String dest) {
        this.dest = dest;
        wordDict = new TrieRoot();

        prevWord2 = new TrieRoot();
        prevWordCount2 = new TrieRoot();

        prevWord3 = new TrieRoot();
        prevWordCount3 = new TrieRoot();

        prevWord4 = new TrieRoot();
        prevWordCount4 = new TrieRoot();
    }

    public void process(String source) {
        BufferedReader bufferedReader;
        String line;
        ArrayList<String> lineArrayList;
        HashMap<String, Integer> hashMap;
        String current;
        String wordList;
        try {
            bufferedReader = new BufferedReader(new FileReader(source));
            line = bufferedReader.readLine();
            while (line != null){
                lineArrayList =  new ArrayList<>();
                lineArrayList.add("xx");
                lineArrayList.addAll(Arrays.asList(line.split(" ")));
                lineArrayList.add("yy");
                // Construct wordDict
                for (String aLineArrayList : lineArrayList)
                    if (wordDict.getValue(aLineArrayList) == 0)
                        wordDict.addWord(aLineArrayList);
                // Construct bigram
                for(int i = 0; i < lineArrayList.size() - 1; i++){
                    current = lineArrayList.get(i);
                    wordList = current + " " + lineArrayList.get(i + 1);
                    prevWord2.addWord(wordList);
                    prevWordCount2.addWord(current);
                }
                // Construct 3gram
                for(int i = 0; i < lineArrayList.size() - 2; i++){
                    current = lineArrayList.get(i) + " " + lineArrayList.get(i + 1);
                    wordList = current + " " + lineArrayList.get(i + 2);
                    prevWord3.addWord(wordList);
                    prevWordCount3.addWord(current);
                }
                // Construct 4gram
                for(int i = 0; i < lineArrayList.size() - 3; i++){
                    current = lineArrayList.get(i) + " " + lineArrayList.get(i + 1) + " " + lineArrayList.get(i + 2);
                    wordList = current + " " + lineArrayList.get(i + 3);
                    prevWord4.addWord(wordList);
                    prevWordCount4.addWord(current);
                }
                line = bufferedReader.readLine();
            }
            /*
            System.out.println(wordDict);
            System.out.println(wordDict.size());
            System.out.println();
            System.out.println(prevWord2);
            System.out.println(prevWordCount2);
            System.out.println();
            System.out.println(prevWord3);
            System.out.println(prevWordCount3);
            System.out.println();
            System.out.println(prevWord4);
            System.out.println(prevWordCount4);
            */
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void serializeModel(){
        FileOutputStream fos = null;
        ObjectOutputStream oos;
        try {
            fos = new FileOutputStream(dest + "wordDict.ser");
            oos = new ObjectOutputStream(fos);
            oos.writeObject(wordDict);
            oos.close();
            fos.close();

            fos = new FileOutputStream(dest + "prevWord2.ser");
            oos = new ObjectOutputStream(fos);
            oos.writeObject(prevWord2);
            oos.close();
            fos.close();

            fos = new FileOutputStream(dest + "prevWordCount2.ser");
            oos = new ObjectOutputStream(fos);
            oos.writeObject(prevWordCount2);
            oos.close();
            fos.close();

            fos = new FileOutputStream(dest + "prevWord3.ser");
            oos = new ObjectOutputStream(fos);
            oos.writeObject(prevWord3);
            oos.close();
            fos.close();

            fos = new FileOutputStream(dest + "prevWordCount3.ser");
            oos = new ObjectOutputStream(fos);
            oos.writeObject(prevWordCount3);
            oos.close();
            fos.close();

            fos = new FileOutputStream(dest + "prevWord4.ser");
            oos = new ObjectOutputStream(fos);
            oos.writeObject(prevWord4);
            oos.close();
            fos.close();

            fos = new FileOutputStream(dest + "prevWordCount4.ser");
            oos = new ObjectOutputStream(fos);
            oos.writeObject(prevWordCount4);
            oos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void sample4gramsentence(String start){
        int max;
        int currentValue;
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
