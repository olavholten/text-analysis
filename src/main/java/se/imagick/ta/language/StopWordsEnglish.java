package se.imagick.ta.language;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Olav Holten on 2017-02-03
 */
public class StopWordsEnglish implements StopWords {
    @Override
    public List<String> getWordList() {
        return Arrays.asList("and", "or", "if", "what");
    }

    @Override
    public String getLanguage() {
        return "en";
    }
}
