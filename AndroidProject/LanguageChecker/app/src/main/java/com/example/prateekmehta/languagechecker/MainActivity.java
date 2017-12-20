package com.example.prateekmehta.languagechecker;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.AssetManager;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.prateekmehta.languagechecker.MESSAGE";


    private TextView voiceInput;
    private TextView speakButton;
    private final int REQ_CODE_SPEECH_INPUT = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Ngram();

        voiceInput = (TextView) findViewById(R.id.voiceInput);
        speakButton = (TextView) findViewById(R.id.btnSpeak);

        speakButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                askSpeechInput();
            }
        });
    }

    // Showing google speech input dialog
    private void askSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Hi speak something");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {

        }
    }

    // Receiving speech input
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    System.out.println(result.get(0));
                    intent.putExtra(EXTRA_MESSAGE, result.get(0));
                    startActivity(intent);
                    //voiceInput.setText(result.get(0));
                }
                break;
            }

        }
    }

    /** Called when the user taps the Send button */
    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.editText);
        String message;
        message = editText.getText().toString();
        boolean atleastOneAlpha = message.matches(".*[a-zA-Z]+.*");
        if (!atleastOneAlpha)
            message = "No text entered";

        System.out.println(message);
        //System.out.println("-------------");
        intent.putExtra(EXTRA_MESSAGE, message);

        startActivity(intent);
    }


    private static HashSet<String> wordDict;

    private static HashMap<String, HashMap<String, Integer>> prevWord4;

    public void Ngram() {
        InputStream fis;
        ObjectInputStream ois;
        AssetManager assetManager = getAssets();
        try {
            fis =  assetManager.open("wordDict.ser") ;
            ois = new ObjectInputStream(fis);
            wordDict = (HashSet<String>) ois.readObject();
            System.out.println("()()()()()()() Word Dict" + wordDict.size());
            fis =  assetManager.open("prevWord4.ser") ;
            ois = new ObjectInputStream(fis);
            prevWord4 = (HashMap<String, HashMap<String, Integer>>) ois.readObject();
            System.out.println("()()()()()()() PRev Dict" + prevWord4.size());
            /*
            fis = new FileInputStream( source + "prevWordCount4.ser");
            ois = new ObjectInputStream(fis);
            prevWordCount4 = (HashMap<String, Integer>) ois.readObject();
            */
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static HashSet<Integer> highlightWrongWords(ArrayList<String> input){
        // I is not in the word dict!!!!!!
        // Input example: 1 1 1 There is an apple 2
        HashSet<Integer> indexHashSet = new HashSet<>();
        if (input == null || input.size() < 4)
            return indexHashSet;
        for(int i = 0; i < input.size(); i++) {
            if ((input.get(i).equals("1")))
                continue;
            System.out.println("()()()()()()() Word 2222 Dict" + wordDict.size());
            if(!(wordDict.contains(input.get(i).toLowerCase())))
                indexHashSet.add(i);
        }
        System.out.println(indexHashSet);
        System.out.println(input);
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
                System.out.println("()()()()()()() PREV 222 Dict" + wordDict.size());
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
        return indexHashSet;
    }
}