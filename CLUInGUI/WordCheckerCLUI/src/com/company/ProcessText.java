package com.company;

import java.io.*;
import java.util.*;

// Start string is assumed to be "1"
// End string is assumed to be "2"
public class ProcessText {
    // dest for serialization
    private String dest;
    private HashSet<String> wordDict;
    // bigram data Strutcture
    // Maps to each word to a Hashmap that is a possible next word and number of occurrence
    private HashMap<String, HashMap<String, Integer>> prevWord2;
    // Maps each word to number of its occurrence
    private HashMap<String, Integer> prevWordCount2;

    // 3gram data Strutcture
    // Maps to each 2 words to a Hashmap that is a possible next word and number of occurrence
    private HashMap<String, HashMap<String, Integer>> prevWord3;
    // Maps each word to number of its occurrence
    private HashMap<String, Integer> prevWordCount3;

    // 4gram data Strutcture
    // Maps to each 3 words to a Hashmap that is a possible next word and number of occurrence
    private HashMap<String, HashMap<String, Integer>> prevWord4;
    // Maps each word to number of its occurrence
    private HashMap<String, Integer> prevWordCount4;


    public ProcessText(String dest) {
        this.dest = dest;
        wordDict = new HashSet<>();

        prevWord2 = new HashMap<>();
        prevWordCount2 = new HashMap<>();

        prevWord3 = new HashMap<>();
        prevWordCount3 = new HashMap<>();

        prevWord4 = new HashMap<>();
        prevWordCount4 = new HashMap<>(1);
    }
    public void processDictionary(String source) {
        BufferedReader bufferedReader;
        String line;
        try {
            bufferedReader = new BufferedReader(new FileReader(source));
            line = bufferedReader.readLine();
            while (line != null){
                if(line.matches("[a-zA-Z']+")) {
                    // THERE IS A CHANGE HERE
                    wordDict.add(line.toLowerCase());
                }
                line = bufferedReader.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void process(String source) {
        BufferedReader bufferedReader;
        String line;
        ArrayList<String> lineArrayList;
        HashMap<String, Integer> hashMap;
        String current;
        String next;
        try {
            bufferedReader = new BufferedReader(new FileReader(source));
            line = bufferedReader.readLine();
            while (line != null){
                /*
                lineArrayList =  new ArrayList<>();
                lineArrayList.add("1");
                lineArrayList.addAll(Arrays.asList(line.split(" ")));
                lineArrayList.add("2");

                // Construct wordDict
                for (String aLineArrayList : lineArrayList)
                    if (!wordDict.contains(aLineArrayList))
                        wordDict.add(aLineArrayList);
                // Construct bigram
                for(int i = 0; i < lineArrayList.size() - 1; i++){
                    current = lineArrayList.get(i);
                    next = lineArrayList.get(i + 1);
                    if (prevWordCount2.containsKey(current)){
                        prevWordCount2.put(current, prevWordCount2.get(current) + 1);
                        if(prevWord2.get(current).containsKey(next))
                            prevWord2.get(current).put(next, prevWord2.get(current).get(next) + 1);
                        else
                            prevWord2.get(current).put(next, 1);
                    }
                    else{
                        prevWordCount2.put(current, 1);
                        hashMap = new HashMap<>();
                        hashMap.put(next, 1);
                        prevWord2.put(current, hashMap);
                    }
                }
                // Construct 3gram
                for(int i = 0; i < lineArrayList.size() - 2; i++){
                    current = lineArrayList.get(i) + " " + lineArrayList.get(i + 1);
                    next = lineArrayList.get(i + 2);
                    if (prevWordCount3.containsKey(current)){
                        prevWordCount3.put(current, prevWordCount3.get(current) + 1);
                        if(prevWord3.get(current).containsKey(next))
                            prevWord3.get(current).put(next, prevWord3.get(current).get(next) + 1);
                        else
                            prevWord3.get(current).put(next, 1);
                    }
                    else{
                        prevWordCount3.put(current, 1);
                        hashMap = new HashMap<>();
                        hashMap.put(next, 1);
                        prevWord3.put(current, hashMap);
                    }
                }
                */
                lineArrayList =  new ArrayList<>();
                lineArrayList.add("1");
                lineArrayList.add("1");
                lineArrayList.add("1");
                lineArrayList.addAll(Arrays.asList(line.split(" ")));
                lineArrayList.add("2");
                // Construct 4gram
                for(int i = 0; i < lineArrayList.size() - 3; i++){
                    current = lineArrayList.get(i) + " " + lineArrayList.get(i + 1) + " " + lineArrayList.get(i + 2);
                    next = lineArrayList.get(i + 3);
                    if (prevWordCount4.containsKey(current)){
                        prevWordCount4.put(current, prevWordCount4.get(current) + 1);
                        if(prevWord4.get(current).containsKey(next))
                            prevWord4.get(current).put(next, prevWord4.get(current).get(next) + 1);
                        else
                            prevWord4.get(current).put(next, 1);
                    }
                    else{
                        prevWordCount4.put(current, 1);
                        hashMap = new HashMap<>(1);
                        hashMap.put(next, 1);
                        prevWord4.put(current, hashMap);
                    }
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
            /*
            fos = new FileOutputStream(dest + "prevWord2.ser");
            oos = new ObjectOutputStream(fos);
            // oos.writeObject(prevWord2);
            oos.close();
            fos.close();
            */
            /*
            fos = new FileOutputStream(dest + "prevWordCount2.ser");
            oos = new ObjectOutputStream(fos);
            oos.writeObject(prevWordCount2);
            oos.close();
            fos.close();
            */
            /*
            fos = new FileOutputStream(dest + "prevWord3.ser");
            oos = new ObjectOutputStream(fos);
            // oos.writeObject(prevWord3);
            oos.close();
            fos.close();
            */
            /*
            fos = new FileOutputStream(dest + "prevWordCount3.ser");
            oos = new ObjectOutputStream(fos);
            oos.writeObject(prevWordCount3);
            oos.close();
            fos.close();
            */
            fos = new FileOutputStream(dest + "prevWord4.ser");
            oos = new ObjectOutputStream(fos);
            oos.writeObject(prevWord4);
            oos.close();
            fos.close();
            /*
            fos = new FileOutputStream(dest + "prevWordCount4.ser");
            oos = new ObjectOutputStream(fos);
            oos.writeObject(prevWordCount4);
            oos.close();
            fos.close();
            */
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    public void sample4gramsentence(String start){
        int max;
        String current = "1 " + start;
        String nextString = null;
        String temp;
        System.out.print(start + " ");
        while (true) {
            max = -1;
            for(String next: prevWord4.get(current).keySet())
                if(prevWord4.get(current).get(next) > max) {
                    max = prevWord4.get(current).get(next);
                    nextString = next;
                }
            if(nextString.equals("2"))
                break;
            temp = current.split(" ")[1];
            temp += " " + current.split(" ")[2];
            temp += " " + nextString;
            current = temp;
            System.out.print(nextString + " ");
        }
        System.out.println();
    }
    public void sample3gramsentence(String start){
        int max;
        String current = "1 " + start;
        String nextString = null;
        String temp;
        System.out.print(start + " ");
        while (true) {
            max = -1;
            for(String next: prevWord3.get(current).keySet())
                if(prevWord3.get(current).get(next) > max) {
                    max = prevWord3.get(current).get(next);
                    nextString = next;
                }
            if(nextString.equals("2"))
                break;
            temp = current.split(" ")[1];
            temp += " " + nextString;
            current = temp;
            System.out.print(nextString + " ");
        }
        System.out.println();
    }
    */
}
