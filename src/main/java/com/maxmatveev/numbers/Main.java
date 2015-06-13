package com.maxmatveev.numbers;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Set;

/**
 * Created by Max Matveev on 11/06/15.
 */
public class Main {
    public static void main(String[] args) throws URISyntaxException, IOException {
        File dictionaryFile;
        File inputFile;

        // take dictionary & input files from parameters or use default ones if parameters are missing
        if (args.length > 0) {
            dictionaryFile = new File(args[0]);
        } else {
            URI dictionaryUri = Main.class.getClassLoader().getResource("dictionary.txt").toURI();
            dictionaryFile = new File(dictionaryUri);
        }

        if (args.length > 1) {
            inputFile = new File(args[1]);
        } else {
            URI inputUri = Main.class.getClassLoader().getResource("input.txt").toURI();
            inputFile = new File(inputUri);
        }

        // load dictionary
        DictionaryBuilder dictionaryBuilder = new DictionaryBuilder();
        Map<String, Set<String>> dictionary = dictionaryBuilder.loadDictionary(dictionaryFile);
        // generate encodings to stdout
        NumberEncoder encoder = new NumberEncoder();
        encoder.encode(inputFile, dictionary);
    }
}
