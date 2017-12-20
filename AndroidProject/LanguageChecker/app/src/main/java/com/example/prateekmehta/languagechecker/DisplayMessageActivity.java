package com.example.prateekmehta.languagechecker;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.io.*;
import java.util.*;

public class DisplayMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        getSupportActionBar().setHomeButtonEnabled(false);      // Disable the button
        getSupportActionBar().setDisplayHomeAsUpEnabled(false); // Remove the left caret
        getSupportActionBar().setDisplayShowHomeEnabled(false); // Remove the icon
        
        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();

        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        System.out.println("+++++++++++++" + message);

        ArrayList<String> list_of_str = Preprocess(message);
        System.out.println("*********&&&&&&&&&&&&&:    " + list_of_str.size());

        HashSet<Integer> result = MainActivity.highlightWrongWords(list_of_str);
        System.out.println("__+++++___+__+++========" + result);
        int max = Collections.max(result);
        result.remove(max);
        System.out.println("__+++++___+__+++========22" + result.size());
        // Capture the layout's TextView and set the string as its text

        TextView textView = findViewById(R.id.textView);

        //use a loop to change text color
        Spannable WordtoSpan = new SpannableString(message);

        //String[] myList = message.split("\\W+");
        //System.out.println("+++++++++++++" + myList.length);
        for (Integer j : result) {
            //word of your list
            String word = String.valueOf(list_of_str.get(j));
            //find index of words
            for (int i = -1; (i = message.indexOf(word, i + 1)) != -1; i++) {
                //find the length of word for set color
                int last = i + word.length();
                WordtoSpan.setSpan(new ForegroundColorSpan(Color.RED), i, last, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        textView.setText(WordtoSpan);

        //textView.setText(message);
        System.out.println(message);
    }

    public ArrayList<String> Preprocess(String sourceFile) {

        String line = sourceFile;

        String sentence;
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

        linkSearch = line.split(" ");
        for (int i = 0; i< linkSearch.length; i++) {
            Matcher myMatcher = pattern.matcher(linkSearch[i]);
            if (myMatcher.find())
                linkSearch[i] = "the website";
        }
        line = TextUtils.join(" ", linkSearch);
        correctedWordList = new ArrayList<>();
        //boolean isEnded = line.charAt(line.length() - 1) == '.' || line.charAt(line.length() - 1) == '?'
        //        || line.charAt(line.length() - 1) == '!';
        parsedLine = line.split("[.!?]");
        for(int index = 0; index < parsedLine.length; index++){
            sentence = parsedLine[index];
            wordList = sentence.split(" ");

            correctedWordList.add("1");
            correctedWordList.add("1");
            correctedWordList.add("1");

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
            correctedWordList.add("2");
        }

        return correctedWordList;
    }
}
