package se.imagick.ta.filter;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Olav Holten on 2017-02-04
 */
public class Decomposer {

    public static List<String> toSentences(String content) {
        // TODO better decomp needed (parentheses, question mark, comma etc).
        return Arrays.asList(content.split("."));// Blunt impl...
    }

    public static List<String> scentenceToWords(String sentence) {

        return Arrays.asList(sentence.split(" "));// Blunt impl...
    }
}
