package com.maxmatveev.numbers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Created by Max Matveev on 11/06/15.
 */
public class NumberEncoder {
    /**
     * Iterate through the input lines and print all matching encodings to stdout
     * in the format as per problem statement
     * @param numbers
     * @param dictionary
     * @throws IOException
     */
    public void encode(File numbers, Map<String, Set<String>> dictionary) throws IOException {
        // iterate through input file lines
        try (Stream<String> lines = Files.lines(numbers.toPath())) {
            // generate encodings for each line
            lines.forEach(line -> generateEncodings(line, line.replaceAll("[^0-9]", ""), dictionary, new ArrayList<>()));
        }
    }

    /**
     * Recursive function to find matching prefixed to the remaining part of the number (= suffix)
     * @param fullNumber original number
     * @param suffix     remaining part of the number (after removing dashes/slashes
     * @param dictionary dictionary (@see DictionaryBuilder javadoc)
     * @param current    current sequence of words matching already processed prefix
     */
    private void generateEncodings(String fullNumber, String suffix, Map<String, Set<String>> dictionary, List<String> current) {
        // if suffix is empty number is fully matched
        if (suffix.isEmpty()) {
            // so reconstruct encoding using <current> values and print it in the required format
            StringBuilder res = new StringBuilder();
            // original number
            res.append(fullNumber);
            // separator
            res.append(":");
            // space-separated words
            current.forEach(piece -> res.append(" ").append(piece));
            System.out.println(res.toString());
            return;
        }
        // going to track if any match was found so can use a single digit as a last resort
        boolean foundMatch = false;
        // trying to match increasing length prefixes starting from 1 character to full remaining part of the number
        for (int i = 1; i <= suffix.length(); i++) {
            // got prefix
            String prefix = suffix.substring(0, i);
            // if dictionary match
            if (dictionary.containsKey(prefix)) {
                // not going to use single-digit encoding
                foundMatch = true;
                // generate remaining non-encoded part of the number
                String newSuffix = suffix.substring(prefix.length());
                // for each mappable word
                dictionary.get(prefix).forEach(encoded -> {
                    // add it as a candidate
                    current.add(encoded);
                    // and recursively call
                    generateEncodings(fullNumber, newSuffix, dictionary, current);
                    // remove just used part so can try next one
                    current.remove(current.size() - 1);
                });
            }
        }
        // if there were no matches
        if (!foundMatch) {
            // if previous match was a single-digit
            if (!current.isEmpty() && current.get(current.size() - 1).matches("[0-9]")) {
                // terminate as it's not possible to encode with given rules/dictionary
                return;
            }
            // take remaining not encoded part
            String newSuffix = suffix.substring(1);
            // add single-digit match to <current>
            current.add(suffix.substring(0, 1));
            // recursively call
            generateEncodings(fullNumber, newSuffix, dictionary, current);
            // remove just used part so can try next one
            current.remove(current.size() - 1);
        }
    }
}
