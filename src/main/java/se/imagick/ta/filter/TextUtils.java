package se.imagick.ta.filter;

import se.imagick.ta.language.StopWordList;
import se.imagick.ta.tfidf.Term;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * Created by Olav Holten on 2016-12-10
 */
@SuppressWarnings("WeakerAccess")
public class TextUtils {

    public static final char NEW_LINE = 10;
    private static final String WORD_DEVIDER = "-";
    private static final char[] SENTENCE_DIVIDER_CHARS = "!?,.;:".toCharArray();
    public static final String REGEX_PREFIX = "\\";

    public static boolean isAlphaOrDashOrSpaceOrSentenceDivider(int chararcter) {
        return chararcter == 32 || chararcter == 45 || isAlpha(chararcter) || isSentenceDivider(chararcter);
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

        sentenceList.removeIf(str -> str.trim().isEmpty());

        return sentenceList;
    }

    public static List<String> split(List<String> fragments, char ch) {
        return fragments.stream()
                .flatMap(str -> Arrays.stream(str.split(REGEX_PREFIX + ch)))
                .map(String::trim)
                .filter(str -> !str.isEmpty())
                .collect(Collectors.toList());
    }

    public static String addSpaceToEndOfLine(String line) {

        line = line.trim();
        return (line.endsWith(WORD_DEVIDER)) ? line.substring(0, line.length() - 1) : line + " ";
    }

    /**
     * Devides a sentence into words. All unwanted characters must be taken away before calling this method
     * EG sentence deviders (punctuation marks, commas, semicolon etc).
     *
     * @param sentence The sentence to be divided
     * @return A List of all words in order of appearance in the sentence.
     */
    public static List<String> devideSentenceIntoWords(String sentence) {

        String[] wordArray = sentence.toLowerCase().split(" ");
        return Arrays.stream(wordArray)
                .map(String::trim)
                .filter(w -> !w.replace(WORD_DEVIDER, "").trim().isEmpty())
                .collect(Collectors.toList());
    }

    /**
     * Retrieves all terms in a sentence as a list of a list of words.
     *
     * @param sentence               The sentence for which to retrieve the terms.
     * @param maxNoOfWordsInTerms    The max number of words in a term. Eg 3 will retrieve words, be-grams and tri-grams.
     * @param stopWordListCollection Stop word lists.
     * @param parseType              Specifies how the content will be impacted by this stop word list.
     * @return A list of list of words building up the terms found (including duplicates).
     */
    public static List<Term> getAllTerms(String sentence, int maxNoOfWordsInTerms, List<StopWordList> stopWordListCollection, ParseType parseType) {

        List<String> wordList = devideSentenceIntoWords(sentence);
        int size = wordList.size();
        List<Term> termList = new ArrayList<>();

        for (int noOfWords = 1; noOfWords <= maxNoOfWordsInTerms; noOfWords++) {
            for (int termNo = 0; termNo + noOfWords <= size; termNo++) {
                List<String> termWordList = new ArrayList<>();
                for (int wordNo = termNo; wordNo < termNo + noOfWords; wordNo++) {
                    String word = wordList.get(wordNo);
                    termWordList.add(word);
                }

                stripStopWordsFromTerm(termWordList, stopWordListCollection, parseType);

                if (!termWordList.isEmpty()) {
                    termList.add(new Term(termWordList));
                }
            }
        }

        return termList;
    }

    public static void stripStopWordsFromTerm(List<String> termWordList, List<StopWordList> stopWordListCollection, ParseType parseType) {
        if (stopWordListCollection != null) {

            if (parseType == ParseType.REMOVE_ALL_STOP_WORDS) {
                for (StopWordList stopWordList : stopWordListCollection) {
                    termWordList.removeIf(stopWordList::isStopWord);
                }
            } else if (parseType == ParseType.REMOVE_TERMS_WITH_ONLY_STOP_WORDS) {

                boolean isOnlyStopWords = false;
                for (StopWordList stopWordList : stopWordListCollection) {
                    int noOfStopWords = 0;

                    for (String word : termWordList) {
                        if (stopWordList.isStopWord(word))
                            noOfStopWords++;
                    }

                    isOnlyStopWords = (noOfStopWords == termWordList.size()) || isOnlyStopWords;
                }

                if (isOnlyStopWords) {
                    termWordList.clear();
                }
            }
        }
    }

    /**
     * Retrieves all terms in a sentence as a list of a list of words.
     *
     * @param sentence           The sentence for which to retrieve the terms.
     * @param maxNoOfWordsInTerm The max number of words in a term. Eg 3 will retrieve words, be-grams and tri-grams.
     * @return A list of list of words building up the terms found (including duplicates).
     */
    public static List<String> getAllTermsAsConcatStrings(String sentence, int maxNoOfWordsInTerm, List<StopWordList> stopWordListCollection) {


        List<Term> allTerms = getAllTerms(sentence, maxNoOfWordsInTerm, stopWordListCollection, null);

        List<String> termList = new ArrayList<>();
        allTerms.forEach(term -> termList.add(term.getJoinedTerm()));

        return termList;
    }

    public static String join(List<String> wordList) {
        StringJoiner sj = new StringJoiner(" ");
        wordList.forEach(sj::add);
        return sj.toString().trim();
    }

    public static String cleanString(String content) {

        StringBuilder sb = new StringBuilder();
        content.codePoints()
                .map(c -> (isSpecialDevider(c)) ? ' ' : c)
                .filter(TextUtils::isAlphaOrDashOrSpaceOrSentenceDivider)
                .forEach(e -> sb.append(Character.toChars(e)));

        return sb.toString();
    }

    public static boolean isSpecialDevider(int c) {
        return c == '&'
                || c == '('
                || c == ')'
                || c == '['
                || c == ']'
                ;
    }
}
