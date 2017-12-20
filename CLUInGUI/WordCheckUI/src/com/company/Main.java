package com.company;

import java.io.File;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class Main {

    public static void main(String[] args) {
        // write your code here

        File folder;
        File[] listOfFiles;
        Preprocess preprocess;
        ProcessText processText;
        Ngram ngramModel;

        // Construct PreprocessedTrainingFiles directory from TrainigFiles directory using Preprocessor class
        folder = new File("./dataset/TrainingFiles");
        listOfFiles = folder.listFiles();

        for (File file : listOfFiles) {
            // Make it sure that file ends with ".txt"
            if(file.getName().endsWith(".txt")) {
                preprocess = new Preprocess("./dataset/TrainingFiles/" + file.getName(),
                        "./dataset/PreprocessedTrainingFiles/" + file.getName());
            }
        }

        // Construct wordlist files for 2, 3, and 4 ngram models
        folder = new File("./dataset/PreprocessedTrainingFiles");
        listOfFiles = folder.listFiles();
        processText = new ProcessText("./dataset/NGramData/");
        processText.processDictionary("./dataset/words.txt");
        for (File file : listOfFiles) {
            // Make it sure that file ends with ".txt"
            if(file.getName().endsWith(".txt")) {
                processText.process("./dataset/PreprocessedTrainingFiles/" + file.getName());
            }
        }
        // System.out.println("Right after processing");
        // processText.sample4gramsentence("You think");


        // processText.sample3gramsentence("He");
        processText.serializeModel();
        System.out.println();
        System.out.println("Serialization is done.");
        ngramModel = new Ngram("./dataset/NGramData/");
        System.out.println("With Ngram!");

        ArrayList<String> input = new ArrayList<>();
        input.add("1");
        input.add("1");
        input.add("1");
        input.add("My");
        input.add("name");
        input.add("is");
        input.add("John");
        input.add("My");
        input.add("name");
        input.add("John");
        input.add("John");
        input.add("2");
        input.add("1");
        input.add("1");
        input.add("1");
        input.add("My");
        input.add("name");
        input.add("is");
        input.add("John");
        input.add("2");
        // Example sentence: My name is John, my name is John. My name is John
        HashSet<Integer> result;
        result = ngramModel.highlightWrongWords(input);
        System.out.println(result);
        // ngramModel.sample4gramsentence("You think");
        // ngramModel.sample3gramsentence("He");


        /*
        // File folder;
        // File[] listOfFiles;
        // Preprocess preprocess;
        ProcessTextTrie processTextTrie;
        NgramTrie ngramModelTrie;

        // Construct PreprocessedTrainingFiles directory from TrainigFiles directory using Preprocessor class
        folder = new File("./dataset/TrainingFiles");
        listOfFiles = folder.listFiles();

        for (File file : listOfFiles) {
            // Make it sure that file ends with ".txt"
            if(file.getName().endsWith(".txt")) {
                preprocess = new Preprocess("./dataset/TrainingFiles/" + file.getName(),
                        "./dataset/PreprocessedTrainingFiles/" + file.getName());
            }
        }

        // Construct wordlist files for 2, 3, and 4 ngram models
        folder = new File("./dataset/PreprocessedTrainingFiles");
        listOfFiles = folder.listFiles();
        processTextTrie = new ProcessTextTrie("./dataset/NGramDataTrie/");
        for (File file : listOfFiles) {
            // Make it sure that file ends with ".txt"
            if(file.getName().endsWith(".txt")) {
                processTextTrie.process("./dataset/PreprocessedTrainingFiles/" + file.getName());
            }
        }
        System.out.println("Right after processing");
        processTextTrie.sample4gramsentence("You think");
        processTextTrie.sample3gramsentence("He");
        processTextTrie.serializeModel();
        System.out.println("Serialization is done.");
        ngramModelTrie = new NgramTrie("./dataset/NGramDataTrie/");
        System.out.println("With Ngram!");
        ngramModelTrie.sample4gramsentence("You think");
        ngramModelTrie.sample3gramsentence("He");
        */
    }


}
