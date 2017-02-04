package se.imagick.ta.language;

import java.util.Collections;
import java.util.List;

/**
 * Detects a document language by filtering it with several stop word lists.
 * The language associated with the stop word list that filters out the most words
 * is assumed to be the correct one.
 */
public class LanguageDetector {
    public static  String detect(String document){
        return detect(document, Collections.singletonList(new StopWordsEnglish()));
    }

    private static String detect(String document, List<StopWords> stopWordsList) {
        return null;
    }
}
