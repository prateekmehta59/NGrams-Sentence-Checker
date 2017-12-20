package com.company;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class Main {

    //Minimum number of occurrence in order to keep the word
    public static Integer Tolerance = 3;

    public static Boolean Initialsetup(){
        return ExistOrCreate("./dataset/TrainingFiles") && ExistOrCreate("./dataset/NGramData") &&
                ExistOrCreate("./dataset/PreprocessedTrainingFiles") && ExistOrCreate("./dataset/UsedPre")
                && ExistOrCreate("./dataset/UsedTrainingFile") && ExistOrCreate("./dataset/UsedPreDone");
    }

    private static boolean ExistOrCreate(String path){
        try {
            File f = new File(path);
            if (!f.exists()){
                f.createNewFile();
            }
        }catch (Exception e){
            return false;
        }
        return true;
    }

    public static StringBuilder RnWTxtfile(File file,Integer SplitSize) {
        BufferedReader reader = null;
        StringBuilder str = new StringBuilder();
        Integer index = 0;

        try {
            reader = new BufferedReader(new FileReader(file.getPath()));
        } catch (FileNotFoundException e) {
            System.err.println("Could not open file " + file.getPath());
            System.exit(-1);
        }
        try {
            String text;
            while ((text = reader.readLine()) != null) {
                str.append(text+".");
                //check if its exceed the size
                if (str.length()>SplitSize){
                    try {
                        index++;
                        String tempname = file.getParent()+"/Split"+index+file.getName();
                        if (!tempname.endsWith(".txt")){
                            tempname += ".txt";
                        }
                        PrintWriter writer = new PrintWriter(tempname, "UTF-8");
                        writer.println(String.join(" ", str));
                        writer.close();
                    }catch (Exception e){
                        System.out.print("Sorry didnt write antyhing for File "+ file.getParent()+"/Split"+index+file.getName());
                    }
                    //clear StringBuilder
                    str.delete(0,str.length());
                }
            }
        } catch (IOException e) {
            System.err.println("I/O error on file " + file.getPath());
            System.exit(-1);
        }
        return str;
    }

    public static void main(String[] args) {
        // write your code here

        File folder;
        File[] listOfFiles;
        Preprocess preprocess;
        ProcessText processText;
        Ngram ngramModel;
        int i;

        if (!Initialsetup()){
            System.out.println("Folder init failed");
        }

        // Construct PreprocessedTrainingFiles directory from TrainigFiles directory using Preprocessor class
        folder = new File("./dataset/TrainingFiles");

        listOfFiles = folder.listFiles();


        //presplit txt file
        for (File file : listOfFiles) {
            // Make it sure that file ends with ".txt"
            // if it is more than 5M, it is too big
            //if (file.getName().equals("bigfile.txt")) {
                Integer SplitSize = 4000;
                RnWTxtfile(file, SplitSize);
                if (!file.delete()) {
                    System.out.print("please delete " + file.getName() + " yourself");
                }
            //}
        }
        System.out.println("All txt files are under controlled size now");

        listOfFiles = folder.listFiles();

        i = 0;
        for (File file : listOfFiles) {
            // Make it sure that file ends with ".txt"
            if(file.getName().endsWith(".txt")) {
                preprocess = new Preprocess("./dataset/TrainingFiles/" + file.getName(),
                        "./dataset/PreprocessedTrainingFiles/" + file.getName());
            }
            try {
                Files.move(file.toPath(), new File("./dataset/UsedTrainingFiles/" + file.getName()).toPath(), REPLACE_EXISTING);
            }catch (Exception e){
                System.out.print("Did not move file");
            }
            i++;
            if(i % 1000 == 0)
                System.out.println(i);
        }
        System.out.println("Preprocessing is done");

        // Construct wordlist files for 2, 3, and 4 ngram models
        folder = new File("./dataset/PreprocessedTrainingFiles");
        listOfFiles = folder.listFiles();
        processText = new ProcessText("./dataset/NGramData/");
        processText.processDictionary("./dataset/words.txt");
        i = 0;
        for (File file : listOfFiles) {
            // Make it sure that file ends with ".txt"
            if(file.getName().endsWith(".txt")) {
                processText.process("./dataset/PreprocessedTrainingFiles/" + file.getName());
            }

            i++;
            if(i % 10 == 0) {

                System.out.println(i);
                System.gc();
            }
            if(i % 100 == 0) {
                System.out.println(i);
                processText.serializeModel();
                System.out.println(i);
            }
        }
        System.out.println("Start serialization!");
        // System.out.println("Right after processing");
        // processText.sample4gramsentence("You think");
        /*
        ArrayList<String> input = new ArrayList<>();
        input.add("I");
        input.add("think");
        input.add("more");
        ArrayList<Integer> result;
        result = processText.highlightWrongWords(input);
        System.out.println(result);
        */



        // processText.sample3gramsentence("He");
        processText.serializeModel();
        System.out.println("Serialization is done.");
        // ngramModel = new Ngram("./dataset/NGramData/");
        // System.out.println("With Ngram!");
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
