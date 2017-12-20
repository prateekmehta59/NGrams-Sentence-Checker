
import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

import com.company.Ngram;

public class CLUI {

    //yellow foreground and black background colored char + char
    private static String CHARColor = "\033[" + 33 + ";" + 40 + ";1m";
    private static String RetoreColor = "\033[" + 0 + ";" + 0 + ";0m";
    private static Ngram ngram;

    public enum CmdKey {
        Exit(":quit"), Help("-help"), FileInput("-file"),
        LineInput("-line"),Done(":done");
        private String cmd;
        private CmdKey(String command) {
            this.cmd = command;
        }
        public boolean IsInput(String command){
            if (command.equals(Help.cmd)) {
                HelpCmds();
            }
            return command.equals(this.cmd);
        }
        @Override
        public String toString(){
            return cmd;
        }
    }

    private static void HelpCmds(){
        StringBuilder helps = new StringBuilder();
        helps.append("[To Exit | "+CmdKey.Exit.toString()+"]\n");
        helps.append("[To Get Help | "+CmdKey.Help.toString()+"]\n");
        helps.append("[File Input | "+CmdKey.FileInput.toString()+"]\n");
        helps.append("[Type Input Line | "+CmdKey.LineInput.toString()+"]\n");
        helps.append("[Exit Input Line Mode | "+CmdKey.Done.toString()+"]\n");
        System.out.print(helps);
    }
    private static void highlightline(String input){
        ArrayList<String> inputList = getParsedString(input);
        HashSet<Integer> result = ngram.highlightWrongWords(inputList);
        for (int i = 0; i < inputList.size(); i++){
            if(inputList.get(i).equals("1") || inputList.get(i).equals("2"))
                continue;
            if (result.contains(i))
                System.out.print(CHARColor + inputList.get(i) + RetoreColor);
            else
                System.out.print(inputList.get(i));
            System.out.print(" ");
        }
        System.out.print("\n");
    }


    public static ArrayList readTxtfile(String filename) {
        BufferedReader reader = null;
        ArrayList<String> str = new ArrayList<>();

        try {
            reader = new BufferedReader(new FileReader(filename));
        } catch (FileNotFoundException e) {
            System.err.println("Could not open file " + filename);
            System.exit(-1);
        }
        try {
            String text;
            while ((text = reader.readLine()) != null) {
                str.add(text);
            }
        } catch (IOException e) {
            System.err.println("I/O error on file " + filename);
            System.exit(-1);
        }
        return str;
    }

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
        return string_to_n_gram;
    }

    public static void main(String args[]){
        System.out.println("loading pre-trained model...");
        ngram = new Ngram("../dataset/NGramData/");
        Scanner keyboard = new Scanner(System.in);
        boolean exit = false;
        while (!exit) {
            System.out.println("Enter command ("+CmdKey.Help.toString()+") ("+CmdKey.Exit.toString()+"):(press enter after every option)");
            String input = keyboard.nextLine();
            if(input != null) {

                //exit program
                if (CmdKey.Exit.IsInput(input)) {
                    System.out.println("Exit WordChecker");
                    exit = true;
                }
                //open command help
                else if(CmdKey.Help.IsInput(input)){
                    HelpCmds();
                }

                //Handle sentence by sentence
                else if (CmdKey.LineInput.IsInput(input)) {
                    while(keyboard.hasNext()  ){
                        input = keyboard.nextLine();
                        if (CmdKey.Done.IsInput(input)){
                            break;
                        }
                        System.out.println("Please see below for correction:\n");
                        //Do Highlighting here
                        highlightline(input);
                    }
                }
                else if (CmdKey.FileInput.IsInput(input)) {
                    //Do Highlighting here
                    System.out.print("The file path in relate to current director: \n");
                    ArrayList<String> txt = readTxtfile(System.getProperty("user.dir")+"/"+keyboard.nextLine());
                    for (String line:txt) {
                        highlightline(line);
                    }

                }
            }
        }
        keyboard.close();
    }
}
