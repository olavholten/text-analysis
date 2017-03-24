package se.imagick.ta.misc;

/**
 * Created by Olav Holten on 2017-02-04
 */
public class Decomposer {

//    public static List<String> documentToSentences(String content) {
//        // TODO better decomp needed (parentheses, question mark, comma etc).
//        List<String> sentenceList = Arrays.stream(content.split("\\."))
//                .map(String::toLowerCase)
//                .flatMap(e -> Arrays.stream(e.split("!")))
//                .flatMap(e -> Arrays.stream(e.split(",")))
//                .flatMap(e -> Arrays.stream(e.split(";")))
//                .flatMap(e -> Arrays.stream(e.split(":")))
//                .flatMap(e -> Arrays.stream(e.split("\\?")))
//                .flatMap(e -> Arrays.stream(e.split("\\(")))
//                .flatMap(e -> Arrays.stream(e.split("\\)")))
//                .map(String::trim)
//                .collect(Collectors.toList());
//
//        return sentenceList;
//    }
//
//    public static List<String> scentenceToTerms(String sentence) {
//        // TODO fix for bi, tri and x-terms.
//        return Arrays.stream(sentence.split(" ")).map(String::trim).filter(e -> !e.isEmpty()).collect(Collectors.toList());
//    }
//
}
