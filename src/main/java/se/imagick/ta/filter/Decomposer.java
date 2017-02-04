package se.imagick.ta.filter;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Olav Holten on 2017-02-04
 */
public class Decomposer {

    public static List<String> documentToSentences(String content) {
        // TODO better decomp needed (parentheses, question mark, comma etc).
        return Arrays.asList(content.split("."));// Blunt impl...
    }

    public static List<String> scentenceToTerms(String sentence) {

        return Arrays.asList(sentence.split(" "));// Blunt impl...
    }

    public static List<String> termsToWords(String term) {

        return Arrays.asList(term.split(" "));// Blunt impl...
    }
}
