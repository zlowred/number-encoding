package com.maxmatveev.numbers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Created by Max Matveev on 11/06/15.
 */
public class DictionaryBuilder {
    // dictionary to map character to the digit
    // Upper/Lower case matches so using just lower case
    private static final Map<Character, Character> encoding = new HashMap<Character, Character>() {{
        put('e', '0');
        put('j', '1');
        put('n', '1');
        put('q', '1');
        put('r', '2');
        put('w', '2');
        put('x', '2');
        put('d', '3');
        put('s', '3');
        put('y', '3');
        put('f', '4');
        put('t', '4');
        put('a', '5');
        put('m', '5');
        put('c', '6');
        put('i', '6');
        put('v', '6');
        put('b', '7');
        put('k', '7');
        put('u', '7');
        put('l', '8');
        put('o', '8');
        put('p', '8');
        put('g', '9');
        put('h', '9');
        put('z', '9');
    }};

    /**
     * Load directory from the file.
     * Resulting map will contain matching number as a key and set of words that can be decoded as that key.
     * @param dictionaryFile
     * @return
     * @throws IOException
     */
    public Map<String, Set<String>> loadDictionary(File dictionaryFile) throws IOException {
        // iterate through the input file lines
        try (Stream<String> lines = Files.lines(dictionaryFile.toPath())) {
            // create resulting map as per javadoc
            Map<String, Set<String>> result = new HashMap<>();
            // for each line in the input file
            lines.forEach(line -> {
                // convert to lower case and remove all non-mappable characters
                String filtered = filter(line);
                // get number that is result of decoding of the word in current line
                String decodedNumber = decode(filtered);
                // create empty set if there are no entries for the current decoded number
                if (!result.containsKey(decodedNumber)) {
                    result.put(decodedNumber, new HashSet<>());
                }
                // put original word mapped to the decoded number
                result.get(decodedNumber).add(line);
            });
            return result;
        }
    }

    /**
     * decode filtered (e.g. lower-case without non-mappable characters) word into number.
     * @param filtered
     * @return
     */
    private String decode(String filtered) {
        // buffer for the decoded number
        char decoded[] = new char[filtered.length()];
        // iterate through characters
        for (int i = 0; i < filtered.length(); i++) {
            // decode char-by-char
            decoded[i] = encoding.get(filtered.charAt(i));
        }
        return new String(decoded);
    }

    /**
     * lowecase the string and regex to remove all non-letter characters
     * @param line
     * @return
     */
    private String filter(String line) {
        return line.toLowerCase().replaceAll("[^a-z]", "");
    }
}
