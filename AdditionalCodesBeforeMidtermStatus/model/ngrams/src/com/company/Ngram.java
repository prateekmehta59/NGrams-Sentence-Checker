package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

class Ngram {
    // Maps to each word to an ArrayList of Pairs that is a possible next word and number of occurrence
    private HashMap<String,HashMap<String, Integer>> prevWord;
    // Maps each word to number of its occurrence
    private HashMap<String,Integer> prevWordCount;
    private ArrayList<String> lineArrayList;
    private HashMap<String, Integer> wordDict;
    private int[][] ngramMatrix;
    Ngram(String path) {
        prevWord = new HashMap<>();
        prevWordCount = new HashMap<>();
        lineArrayList = new ArrayList<>();
        wordDict = new HashMap<>();
        readFile(path);
        constructData();
        // constructNgramMatrix();
    }

    // Storing file in lineArrayList
    // Modify this part and use files coming from crawler ebooks and a word dictionary
    private void readFile(String path){
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
            String line = bufferedReader.readLine();
            while (line != null)
            {
                lineArrayList.add(line);
                line = bufferedReader.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Initializing fields
    private void constructData(){
        HashMap<String, Integer> hashMap;
        for(int index = 0; index < lineArrayList.size(); index++){
            // eg. tokens = ["237", "a", "babysitter"]
            String[] tokens = lineArrayList.get(index).split("\\t");
            if(!wordDict.containsKey(tokens[1]))
                wordDict.put(tokens[1], wordDict.size());
            if(!wordDict.containsKey(tokens[2]))
                wordDict.put(tokens[2], wordDict.size());
            if (prevWordCount.containsKey(tokens[1])){
                prevWordCount.put(tokens[1], prevWordCount.get(tokens[1]) + Integer.valueOf(tokens[0]));
                prevWord.get(tokens[1]).put(tokens[2], Integer.valueOf(tokens[0]));
            }
            else{
                prevWordCount.put(tokens[1], Integer.valueOf(tokens[0]));
                hashMap = new HashMap<>();
                hashMap.put(tokens[2], Integer.valueOf(tokens[0]));
                prevWord.put(tokens[1], hashMap);
            }
        }
        // System.out.println(prevWord);
        // System.out.println(prevWordCount);
        // System.out.println(wordDict);
    }
    // Ngram matrix grows bigger so not included
    private void constructNgramMatrix() {
        /*
        ngramMatrix = new int[wordDict.size()][wordDict.size()];
        int rowindex, colindex;
        String string;
        ArrayList arrayList;
        Pair<String, Integer> pair;
        for (Map.Entry<String, ArrayList<Pair<String, Integer>>> entry : prevWord.entrySet()){
            string = entry.getKey();
            arrayList = entry.getValue();
            rowindex = wordDict.get(string);
            for (Object element : arrayList) {
                pair = (Pair<String, Integer>) element;
                colindex = wordDict.get(pair.getKey());
                ngramMatrix[rowindex][colindex] = pair.getValue();
            }
        }

        for(int i = 0; i<wordDict.size();i++){
            for(int j = 0; j<wordDict.size();j++) {
                System.out.print(ngramMatrix[i][j]);
                System.out.print(' ');
            }
            System.out.println();
        }

        prevWord = null;
        */
    }

    // Returns log probability of string based on the model
    public double stringProb(String sentence){
        // Ignore punctuation as the last character
        String[] words = sentence.substring(0,sentence.length() -1).split(" ");
        for(int i = 0; i < words.length; i++)
            if(!wordDict.containsKey(words[i])){
                System.out.println("The word " + words[i] + " is not in the model");
                return 0;
            }
        double probSum = 0;
        int index = 0;
        while(index + 1 < words.length){
            if(!prevWord.containsKey(words[index])){
                System.out.println("The word " + words[index] + " is not in prevWord");
                return 0;
            }
            double occurrence = 1.0 * prevWord.get(words[index]).getOrDefault(words[index + 1], 0);
            probSum += Math.log((occurrence + 1) / (prevWordCount.get(words[index]) + wordDict.size()));
            index++;
        }
        return probSum;
    }
}
