package se.imagick.ta.filter;

import java.util.List;

/**
 * Created by john on 2017-02-03
 */
public interface StopWords {

    /**
     * Retrieves a language specific list of the most commonly used words (stop words).
     * @return A list of stop words for a specific language.
     */
    public List<String> getWordList();

    /**
     * Retrieves the language for which the stop word list is used.
     * @return
     */
    String getLanguage();
}
