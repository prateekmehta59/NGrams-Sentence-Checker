package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Preprocess {
    private String SourceFile;
    private String DestFile;
    /*
        All websites are replaced with "the website" words to simplify.
        All empty lines are ignored
        All the words (defined as character strings between spaces that have at least one character)
            containing characters except [a-zA-Z'] are ignored.
        The sentence (defined as the word lists that end with !, ., or ?)
        Each sentence is saved into a new line in dest file.
    */
    public Preprocess(String sourceFile, String destFile) {
        SourceFile = sourceFile;
        DestFile = destFile;
        try {
            PrintWriter writer = new PrintWriter(DestFile, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(new FileReader(sourceFile));
            String line = bufferedReader.readLine();

            String sentence;
            StringBuilder unFinishedSentence = new StringBuilder();
            String[] parsedLine;
            String[] wordList;
            ArrayList<String> correctedWordList;
            String[] linkSearch;


            // Pattern to match a website
            Pattern pattern = Pattern.compile(
                    "\\b(((ht|f)tp(s?)\\:\\/\\/|~\\/|\\/)|www.)" +
                            "(\\w+:\\w+@)?(([-\\w]+\\.)+(com|org|net|gov" +
                            "|mil|biz|info|mobi|name|aero|jobs|museum" +
                            "|travel|[a-z]{2}))(:[\\d]{1,5})?" +
                            "(((\\/([-\\w~!$+|.,=]|%[a-f\\d]{2})+)+|\\/)+|\\?|#)?" +
                            "((\\?([-\\w~!$+|.,*:]|%[a-f\\d{2}])+=?" +
                            "([-\\w~!$+|.,*:=]|%[a-f\\d]{2})*)" +
                            "(&(?:[-\\w~!$+|.,*:]|%[a-f\\d{2}])+=?" +
                            "([-\\w~!$+|.,*:=]|%[a-f\\d]{2})*)*)*" +
                            "(#([-\\w~!$+|.,*:=]|%[a-f\\d]{2})*)?\\b");
            boolean isSentenceEnded;
            while (line != null){

                // Empty line just skip it
                if(line.length() == 0){
                    line = bufferedReader.readLine();
                    continue;
                }

                linkSearch = line.split(" ");
                for (int i = 0; i< linkSearch.length; i++) {
                    Matcher myMatcher = pattern.matcher(linkSearch[i]);
                    if (myMatcher.find())
                        linkSearch[i] = "the website";
                }
                line = String.join(" ", linkSearch);

                // Empty line just skip it
                if(line.length() == 0){
                    line = bufferedReader.readLine();
                    continue;
                }

                // Check last character to see if the sentence is completed
                isSentenceEnded = line.substring(line.length() - 1).matches("[.!?]");
                parsedLine = line.split("[.!?]");
                for(int index = 0; index < parsedLine.length; index++){
                    // Concatenate the sentence from previous one
                    if(index == 0)
                        sentence = unFinishedSentence.toString() + ' ' + parsedLine[0];
                    // Else form a new sentence
                    else
                        sentence = parsedLine[index];
                    // If sentence is not ended just concatenate it to unFinishedSentence.
                    if(index == parsedLine.length - 1 && !isSentenceEnded){
                        // If the line is more than two lines
                        if(index == 0) {
                            unFinishedSentence.append(' ').append(parsedLine[index]);
                        } else
                            unFinishedSentence = new StringBuilder(parsedLine[index]);
                        continue;
                    }
                    else
                        unFinishedSentence = new StringBuilder();
                    wordList = sentence.split(" ");
                    correctedWordList = new ArrayList<>();
                    for (String word: wordList) {
                        if(word.length() == 0)
                            continue;
                        // Check it words start or ends with some other characters eg. '(,:)'
                        if((word.charAt(0) == '\'') || (word.charAt(0) == '"') || (word.charAt(0) == '(')) {
                            if (word.length() == 1)
                                continue;
                            else
                                word = word.substring(1);
                        }
                        if((word.charAt(word.length()-1) == '\'') || (word.charAt(word.length()-1) == '"') ||
                                (word.charAt(word.length()-1) == ')') || (word.charAt(word.length()-1) == ':') ||
                                (word.charAt(word.length()-1) == ',')) {
                            if (word.length() == 1)
                                continue;
                            else
                                word = word.substring(0, word.length() - 1);
                        }
                        if(word.matches("[a-zA-Z']+"))
                            correctedWordList.add(word);
                    }
                    // Write it if there is a sentence
                    if(correctedWordList.size() != 0)
                        writer.println(String.join(" ", correctedWordList));
                }
                line = bufferedReader.readLine();
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
