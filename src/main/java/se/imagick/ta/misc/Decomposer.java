package se.imagick.ta.misc;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Olav Holten on 2017-02-04
 */
public class Decomposer {

    public static List<String> documentToSentences(String content) {
        // TODO better decomp needed (parentheses, question mark, comma etc).
        List<String> sentenceList = Arrays.stream(content.split("\\."))
                .map(String::toLowerCase)
                .flatMap(e -> Arrays.stream(e.split("!")))
                .flatMap(e -> Arrays.stream(e.split(",")))
                .flatMap(e -> Arrays.stream(e.split(";")))
                .flatMap(e -> Arrays.stream(e.split(":")))
                .flatMap(e -> Arrays.stream(e.split("\\?")))
                .flatMap(e -> Arrays.stream(e.split("\\(")))
                .flatMap(e -> Arrays.stream(e.split("\\)")))
                .map(String::trim)
                .collect(Collectors.toList());

        return sentenceList;
    }

    public static List<String> scentenceToTerms(String sentence) {
        // TODO fix for bi, tri and x-terms.
        return Arrays.stream(sentence.split(" ")).map(String::trim).filter(e -> !e.isEmpty()).collect(Collectors.toList());
    }

    public static List<String> termsToWords(String term) {
        return Arrays.stream(term.split(" ")).map(String::trim).filter(e -> !e.isEmpty()).collect(Collectors.toList());// Blunt impl...
    }
}
