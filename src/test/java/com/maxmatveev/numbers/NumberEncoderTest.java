package com.maxmatveev.numbers;

import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class NumberEncoderTest {
    /**
     * Encode number set from the example in problem statement
     * using provided dictionary and compare it with results from the statement.
     * @throws URISyntaxException
     * @throws IOException
     */
    @Test
    public void testEncoder() throws URISyntaxException, IOException {
        PrintStream oldSystemOut = System.out;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream newSystemOut = new PrintStream(out);
        System.setOut(newSystemOut);
        Map<String, Set<String>> map = new DictionaryBuilder().loadDictionary(new File(getClass().getClassLoader().getResource("sampleDictionary.txt").toURI()));
        new NumberEncoder().encode(new File(getClass().getClassLoader().getResource("samplePhoneList.txt").toURI()), map);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(out.toByteArray());
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        Set<String> actual = new HashSet<>();
        for (String line; (line = reader.readLine()) != null; ) {
            actual.add(line);
        }
        Set<String> expected = new HashSet<>();
        Files.lines(new File(getClass().getClassLoader().getResource("sampleResults.txt").toURI()).toPath()).forEach(expected::add);
        System.setOut(oldSystemOut);
        Assert.assertEquals(expected, actual);
    }
}