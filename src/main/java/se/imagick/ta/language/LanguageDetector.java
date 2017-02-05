package se.imagick.ta.language;

import se.imagick.ta.misc.Decomposer;

import java.util.List;

/**
 * Detects a document language by filtering it with several stop word lists.
 * The language associated with the stop word list that filters out the most words
 * is assumed to be the correct one.
 */
public class LanguageDetector {

    public static String detect(String document, StopWords... stopWordsList) {

        List<String> words = Decomposer.scentenceToTerms(document);
        String reccordLanguage = "Not detected";
        double reccord = 0;

        for(StopWords stopWords : stopWordsList) {

            double noOfStopWordsInText = (double) words.stream().filter(stopWords::isStopWord).count();

            if (noOfStopWordsInText > reccord) {
                reccord = noOfStopWordsInText;
                reccordLanguage = stopWords.getLanguage();
            }
        }

        return reccordLanguage;
    }

}
