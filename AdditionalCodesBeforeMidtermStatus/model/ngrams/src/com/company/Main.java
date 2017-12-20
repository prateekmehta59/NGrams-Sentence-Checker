package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here
        Ngram ngramModel = new Ngram("./Dataset/w2_.txt");
        System.out.println(ngramModel.stringProb("a car?"));
    }
}
