package se.imagick.ta.language;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Olav Holten on 2017-02-03
 */
public class StopWordsEnglish extends StopWordList {

    @Override
    public List<String> getWordList() {
        return Arrays.asList("the", "and", "of", "to", "a", "he", "in", "was", "that", "his", "i", "it",
                "had", "you", "with", "for", "her", "as", "she", "on", "him", "at", "not", "but", "this",
                "be", "were", "they", "all", "from", "have", "would", "by", "which", "one", "said", "is",
                "an", "or", "so", "if", "there", "no", "me", "up", "what", "who", "their", "could", "out",
                "when", "we", "now", "do", "been", "them", "then", "my", "are", "did", "some", "about",
                "will");
    }

    @Override
    public String getLanguage() {
        return "en";
    }
}
