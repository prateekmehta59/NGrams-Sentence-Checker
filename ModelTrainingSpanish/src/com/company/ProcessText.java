package com.company;

import javafx.util.Pair;
import org.omg.CORBA.Current;

import javax.swing.text.html.HTMLDocument;
import javax.xml.soap.Node;
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

        prevWord4 = new HashMap<>(1);
        prevWordCount4 = new HashMap<>();

        Ngram n = new Ngram("./dataset/NGramData/");
        prevWord4 = n.prevWord4;
        wordDict = n.wordDict;

    }

    public HashMap<String, HashMap<String, Integer>> getPrevWord4() {
        return prevWord4;
    }

    public void processDictionary(String source) {
        BufferedReader bufferedReader;
        String line;
        try {
            bufferedReader = new BufferedReader(new FileReader(source));
            line = bufferedReader.readLine();
            while (line != null){
                if(line.matches("[\\p{L}\\s]+")) {
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
                    if(prevWord4.containsKey(current)) {
                        if (prevWord4.get(current).containsKey(next))
                            prevWord4.get(current).put(next, prevWord4.get(current).get(next) + 1);
                        else
                            prevWord4.get(current).put(next, 1);
                    }
                    else{
                        hashMap = new HashMap<>(1);
                        hashMap.put(next, 1);
                        prevWord4.put(current, hashMap);
                    }
                    /*
                    if (prevWordCount4.containsKey(current)){
                        prevWordCount4.put(current, prevWordCount4.get(current) + 1);
                        if(prevWord4.get(current).containsKey(next))
                            prevWord4.get(current).put(next, prevWord4.get(current).get(next) + 1);
                        else
                            prevWord4.get(current).put(next, 1);
                    }
                    else{
                        prevWordCount4.put(current, 1);
                        hashMap = new HashMap<>();
                        hashMap.put(next, 1);
                        prevWord4.put(current, hashMap);
                    }
                    */
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

    public void DeleteLessOccurrance(Integer tollrance){
        Iterator it = prevWord4.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String,HashMap<String, Integer>> pair = (Map.Entry<String,HashMap<String, Integer>>)it.next();
            if (pair.getValue().size()>0) {
                Iterator itt = pair.getValue().entrySet().iterator();
                while (itt.hasNext()){
                    Map.Entry<String, Integer> p = (Map.Entry<String, Integer>)itt.next();
                    if ( p.getValue()<tollrance){
                        itt.remove();
                    }
                }
            }
            if(pair.getValue().size()==0) {
                it.remove();
            }
        }
        //Iterator.
    }
    public void serializeModel(){
        FileOutputStream fos = null;
        ObjectOutputStream oos;
        DeleteLessOccurrance(Main.Tolerance);
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
    public ArrayList<Integer> highlightWrongWords(ArrayList<String> input){
        ArrayList<Integer> indexList = new ArrayList<>();
        HashSet<Integer> indexHashSet = new HashSet<>();
        if (input == null || input.size() < 2)
            return indexList;
        for(int i = 0; i < input.size(); i++)
            if(!wordDict.contains(input.get(i)))
                indexHashSet.add(i);
        int i = -1, j = -1, k =-1;
        String current;
        while(k < input.size()){
            if(k == -1)
                current
        }
        for(int i = 0; i < input.size(); i++){
            if(!indexHashSet.contains(i)) {
                if(prevWord4.containsKey(current)) {
                    if (!prevWord4.get(current).containsKey(input.get(i)))
                        indexList.add(i);
                }
                else{

                }
                temp = current.split(" ");
                current = temp[1] + " " + temp[2] + " " + input.get(i);
            }

            // If end of a sentence
            if(input.get(i).equals("2"))
                current = "1 1 1";
        }
        return indexList;
    }


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
