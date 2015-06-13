package com.maxmatveev.numbers;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class DictionaryBuilderTest {
    /**
     * Not that much to test.
     * Here test dictionary is being loaded and then
     * few parameters (like size and couple of values) are verified
     *
     * @throws URISyntaxException
     * @throws IOException
     */
    @Test
    public void testDictionaryBuilder() throws URISyntaxException, IOException {
        Map<String, Set<String>> map = new DictionaryBuilder().loadDictionary(new File(getClass().getClassLoader().getResource("sampleDictionary.txt").toURI()));
        assertEquals(21, map.size());
        assertEquals(new HashSet<>(Arrays.asList("Torf", "fort")), map.get("4824"));
        assertEquals(new HashSet<>(Arrays.asList("bo\"s")), map.get("783"));
    }

}