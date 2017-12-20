package com.company;

public class Test {
    // This class makes use of Test1, Test2, Test3 folders for testing purposes.

    public Test() {
        Preprocess preprocess = new Preprocess("dataset/Test/Test1", "dataset/Test/Test2");
        ProcessText processText = new ProcessText("dataset/Test/Test3");
        processText.process("dataset/Test/Test2");
        // Print the model for testing
        System.out.println(processText.getPrevWord4());
        Ngram ngram = new Ngram("dataset/Test/Test3");
        // Print the model for testing
        System.out.println(ngram.prevWord4);
    }


}
