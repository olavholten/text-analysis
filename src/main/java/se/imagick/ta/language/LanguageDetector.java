package se.imagick.ta.language;

import se.imagick.ta.filter.CharacterUtils;
import se.imagick.ta.filter.ParseUtils;
import se.imagick.ta.tfidf.Term;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Detects a document language by filtering it with several stop word lists.
 * The language associated with the stop word list that filters out the most words
 * is assumed to be the correct one. A rather naive method that needs more than a few words.
 * Could also be used to detect the document category by using stop word lists that
 * contains category specific words.
 * TODO add functionality for retrieving percentage of words sorted out by the stop word list.
 */
public class LanguageDetector {

    public static String detect(String document, StopWordList... stopWordsListCollection) {

        IntStream chars = document.codePoints();
        StringBuilder documentSb = new StringBuilder();
        chars.filter(CharacterUtils::isAlphaOrSpace).forEach(e -> documentSb.append(Character.toChars(e)));

        List<Term> termList = ParseUtils.getAllTermsInSentence(documentSb.toString(), 1, new ArrayList<>(), null);
        String reccordLanguage = "Not detected";
        double reccord = 0;

        for (StopWordList stopWords : stopWordsListCollection) {

            double noOfStopWordsInText = (double) termList.stream().filter(term -> stopWords.isStopWord(term.getJoinedTerm())).count();

            if (noOfStopWordsInText > reccord) {
                reccord = noOfStopWordsInText;
                reccordLanguage = stopWords.getLanguage();
            }
        }

        return reccordLanguage;
    }
}
