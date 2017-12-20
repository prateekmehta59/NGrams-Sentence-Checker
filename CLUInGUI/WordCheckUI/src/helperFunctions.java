import java.io.*;
import java.util.ArrayList;

public class helperFunctions {
    private static String COMMAND_TO_TRANSCRIBE_VOICE = "java -cp ../../speech_to_text/target/speech-google-cloud-samples-1.0.0-jar-with-dependencies.jar com.example.speech.Recognize syncrecognize ./RecordAudio.wav";
    private static String COMMAND_TO_TRANSCRIBE_VOICE_TEST = "java -cp ../../speech_to_text/target/speech-google-cloud-samples-1.0.0-jar-with-dependencies.jar com.example.speech.Recognize syncrecognize ./testing_audio.wav";
    private static String[] ENVIRONMENTAL_VARIABLES = {"GOOGLE_APPLICATION_CREDENTIALS=../../speech_to_text/google_speech_api.json"};

    public static ArrayList<String> getParsedString(String in){
        ArrayList<String> string_to_n_gram = new ArrayList<String>();
        if(in == null || in.length() == 0)
            return string_to_n_gram;
        String[] parsedLine = in.split("[.!?\n]");
        String sentence;
        String[] wordList;
        for(int index = 0; index < parsedLine.length; index++){
            sentence = parsedLine[index];
            wordList = sentence.split(" ");
            if(wordList.length == 0)
                continue;
            string_to_n_gram.add("1");
            string_to_n_gram.add("1");
            string_to_n_gram.add("1");
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
                string_to_n_gram.add(word);
            }
            string_to_n_gram.add("2");
        }
        System.out.println(string_to_n_gram);
        return string_to_n_gram;
    }
    public static StringBuffer getTranscibedText(boolean test){
        StringBuffer voice_transcribed = new StringBuffer();
        Runtime run = Runtime.getRuntime();
        String temp = "";
        Process pr;
        try {
            if(test == false)
                pr = run.exec(COMMAND_TO_TRANSCRIBE_VOICE,ENVIRONMENTAL_VARIABLES);
            else
                pr = run.exec(COMMAND_TO_TRANSCRIBE_VOICE_TEST,ENVIRONMENTAL_VARIABLES);
            pr.waitFor();
            BufferedReader buf = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            while ((temp = buf.readLine()) != null) {
                voice_transcribed.append(temp);
                //System.out.println(temp);
            }
        } catch (IOException e2) {
            System.out.println("Not able to execute the command, may be you have to change the directory for the json file");
            e2.printStackTrace();
        } catch (InterruptedException e1) {
            System.out.println("process did not get completed");
            e1.printStackTrace();
        }
        return voice_transcribed;
    }

    public static StringBuilder RTxtfile(File file) {
        BufferedReader reader = null;
        StringBuilder str = new StringBuilder();
        try {
            reader = new BufferedReader(new FileReader(file.getPath()));
        } catch (FileNotFoundException e) {
            System.err.println("Could not open file " + file.getPath());
            System.exit(-1);
        }
        try {
            String text;
            while ((text = reader.readLine()) != null) {
                str.append(text+"\n");
            }
        } catch (IOException e) {
            System.err.println("I/O error on file " + file.getPath());
            System.exit(-1);
        }
        return str;
    }
}
