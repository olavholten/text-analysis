package se.imagick.ta.filter;

import se.imagick.ta.tfidc.Term;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * Created by Olav Holten on 2016-12-10
 */
public class TextUtils {

    private static final char[] SENTENCE_DIVIDER_CHARS = "!?,.;:".toCharArray();

    public static boolean isAlphaOrSpaceOrSentenceDivider(int chararcter) {
        return chararcter == 32 || isAlpha(chararcter) || isSentenceDivider(chararcter);
    }

    public static boolean isAlphaOrSpace(int chararcter) {
        return chararcter == 32 || isAlpha(chararcter);
    }

    public static boolean isSentenceDivider(int character) {

        for (char devider : SENTENCE_DIVIDER_CHARS) { // Faster than indexOf
            if (character == devider) {
                return true;
            }
        }

        return false;
    }

    public static boolean isAlpha(int character) {
        return (character >= 65 && character <= 90)
                || (character >= 97 && character <= 122)
                || (character >= 192 && character <= 214)
                || (character >= 216 && character <= 122)
                || (character >= 216 && character <= 246)
                || (character >= 248 && character <= 450)
                ;
    }

    public static List<String> devideSentences(String text) {

        List<String> sentenceList = new ArrayList<>();
        sentenceList.add(text);

        for (char ch : SENTENCE_DIVIDER_CHARS) {
            sentenceList = split(sentenceList, ch);
        }

        return sentenceList;
    }

    public static List<String> split(List<String> fragments, char ch) {
        return fragments.stream()
                .flatMap(str -> Arrays.stream(str.split("\\" + ch)))
                .map(String::trim)
                .filter(str -> !str.isEmpty())
                .collect(Collectors.toList());
    }

    /**
     * Devides a sentence into words. All sentence dividers must be taken away before calling this method.
     * @param sentence The sentence to be divided
     * @return A List of strings in order of appearance in the sentence.
     */
    public static List<String> devideSentenceIntoWords(String sentence) {

        String[] wordArray = sentence.toLowerCase().split(" ");
        return Arrays.stream(wordArray)
                .map(String::trim)
                .filter(w -> !w.isEmpty())
                .collect(Collectors.toList());
    }

    /**
     * Retrieves all terms in a sentence as a list of a list of words.
     * @param sentence The sentence for which to retrieve the terms.
     * @param maxNoOfWordsInTerm The max number of words in a term. Eg 3 will retrieve words, be-grams and tri-grams.
     * @return A list of list of words building up the terms found (including duplicates).
     */
    public static List<Term> getAllTerms(String sentence, int maxNoOfWordsInTerm) {

        List<String> wordList = devideSentenceIntoWords(sentence);
        int size = wordList.size();
        List<Term> termList = new ArrayList<>();

        for(int noOfWords = 1; noOfWords <= maxNoOfWordsInTerm; noOfWords++) {
            for(int termNo = 0; termNo + noOfWords <= size; termNo++ ) {
                List<String> termWordList = new ArrayList<>();
                for(int wordNo = termNo; wordNo < termNo + noOfWords; wordNo++) {
                    String word = wordList.get(wordNo);
                    termWordList.add(word);
                }
                termList.add(new Term(termWordList));
            }
        }

        return termList;
    }

    /**
     * Retrieves all terms in a sentence as a list of a list of words.
     * @param sentence The sentence for which to retrieve the terms.
     * @param maxNoOfWordsInTerm The max number of words in a term. Eg 3 will retrieve words, be-grams and tri-grams.
     * @return A list of list of words building up the terms found (including duplicates).
     */
    public static List<String> getAllTermsAsConcatStrings(String sentence, int maxNoOfWordsInTerm) {


        List<Term> allTerms = getAllTerms(sentence, maxNoOfWordsInTerm);

        List<String> termList = new ArrayList<>();
        allTerms.forEach(term -> termList.add(term.getJoinedTerm()));

        return termList;
    }

    public static String join(List<String> wordList) {
        StringJoiner sj = new StringJoiner(" ");
        wordList.forEach(sj::add);
        return sj.toString();
    }
}
