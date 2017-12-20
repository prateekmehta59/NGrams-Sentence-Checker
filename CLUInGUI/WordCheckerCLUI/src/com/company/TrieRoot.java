package com.company;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class TrieRoot implements Serializable{
    // Root node has a char of
    private TrieNode root;

    public TrieRoot() {
        this.root = new TrieNode();
    }

    // Adds word to Trie structure
    public void addWord(String word){
        if(word == null)
            return;
        // Convert it to lowercase
        word = word.toLowerCase();
        if(!word.matches("[a-z' ]+"))
            return;
        this.root.addWord(word);
    }

    public int getValue(String word){
        if(word == null)
            return 0;
        // Convert it to lowercase
        word = word.toLowerCase();
        if(!word.matches("[a-z' ]+"))
            return 0;
        return this.root.getLeafValue(word);
    }

    public ArrayList<String> getAllWords(){
        ArrayList<String> result = new ArrayList<>();
        this.root.getAllWords(result, "");
        return result;
    }

    // Get all words starting with a prefix
    public ArrayList<String> getAllWordsPrefix(String prefix){

        ArrayList<String> result = new ArrayList<>();

        if(prefix == null)
            return result;
        // Convert it to lowercase
        prefix = prefix.toLowerCase();
        if(!prefix.matches("[a-z' ]+"))
            return result;
        this.root.getAllWordsPrefix(result, prefix);
        return result;
    }


    public HashMap<Character, TrieNode> getChildHashMap() {
        return root.getChildHashMap();
    }

    /*
    public TrieNode[] getChildArray() {
        return root.getChildArray();
    }
    */
}
class TrieNode implements Serializable{
    // There are 28 possibilities for children array [a-z], ' and space.
    // private TrieNode[] childArray;
    private HashMap<Character, TrieNode> childHashMap;
    private int leafValue = 0;

    public TrieNode() {
        // childArray = new TrieNode[28];
        childHashMap = new HashMap<Character, TrieNode>();
    }

    /*
    public TrieNode[] getChildArray() {
        return childArray;
    }
    */

    public HashMap<Character, TrieNode> getChildHashMap() {
        return childHashMap;
    }

    public void addWord(String word){
        // Increase value by one if we reached end of string
        if(word.length() == 0) {
            leafValue++;
        }
        // Add accordingly
        else {
            if(childHashMap.containsKey(word.charAt(0)))
                childHashMap.get(word.charAt(0)).addWord(word.substring(1));
            else {
                childHashMap.put(word.charAt(0), new TrieNode());
                childHashMap.get(word.charAt(0)).addWord(word.substring(1));
            }
            /*
            int index = -1;
            if(word.charAt(0) != ' ' && word.charAt(0) != '\'')
                index = word.charAt(0) - 'a';
            else if(word.charAt(0) == '\'')
                index = 26;
            else if(word.charAt(0) == ' ')
                index = 27;
            // Initialize then add it
            if(childArray[index] == null) {
                childArray[index] = new TrieNode();
                childArray[index].addWord(word.substring(1));
            }
            else
                childArray[index].addWord(word.substring(1));
            */
        }
    }

    // Return leaf value for such a string
    public int getLeafValue(String word){
        if(word.length() == 0)
            return leafValue;
        if(childHashMap.containsKey(word.charAt(0)))
            return childHashMap.get(word.charAt(0)).getLeafValue(word.substring(1));
        else
            return 0;
        /*
        int index = -1;
        if(word.charAt(0) != ' ' && word.charAt(0) != '\'')
            index = word.charAt(0) - 'a';
        else if(word.charAt(0) == '\'')
            index = 26;
        else if(word.charAt(0) == ' ')
            index = 27;
        if(childArray[index] == null) {
            return 0;
        }
        return childArray[index].getLeafValue(word.substring(1));
        */
    }

    // Add all values to the argument
    public void getAllWords(ArrayList<String> stringArrayList, String stringSoFar){
        for(int i = 0; i < this.leafValue; i++)
            stringArrayList.add(stringSoFar);
        /*
        for(int i = 0; i < 26; i++)
            if(this.childArray[i] != null)
                this.childArray[i].getAllWords(stringArrayList, stringSoFar + Character.toString((char) (i + 'a')));
        if (this.childArray[26] != null)
            this.childArray[26].getAllWords(stringArrayList, stringSoFar + "'");
        if (this.childArray[27] != null)
            this.childArray[27].getAllWords(stringArrayList, stringSoFar + " ");
        */
        for(Character character: childHashMap.keySet()){
            this.childHashMap.get(character).getAllWords(stringArrayList, stringSoFar + character);
        }
    }

    // Get all words starting with a prefix and add them to the argument
    public void getAllWordsPrefix(ArrayList<String> result, String prefix){
        if(prefix.length() == 0)
            this.getAllWords(result, "");
        else{
            if(childHashMap.containsKey(prefix.charAt(0)))
                this.childHashMap.get(prefix.charAt(0)).getAllWordsPrefix(result, prefix.substring(1));
            /*
            int index = -1;
            if (prefix.charAt(0) != ' ' && prefix.charAt(0) != '\'')
                index = prefix.charAt(0) - 'a';
            else if (prefix.charAt(0) == '\'')
                index = 26;
            else if (prefix.charAt(0) == ' ')
                index = 27;
            if(this.getChildArray()[index] != null)
                this.getChildArray()[index].getAllWordsPrefix(result, prefix.substring(1));
            */
        }
    }

}