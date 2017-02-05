package se.imagick.ta.language;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Olav Holten on 2017-02-03
 */
public abstract class StopWords {

    private Map<String, String> sortedWords = new HashMap<>();

    public StopWords(){
        getWordList().forEach(w ->sortedWords.put(w, w));
    }

    /**
     * Detects whether a word is included in this stop word list.
     * @param word The word beeing examined.
     * @return true if this word is in this list.
     */
    public boolean isStopWord(String word){
        return sortedWords.containsKey(word);
    }

    /**
     * Retrieves a language specific list of the most commonly used words (stop words).
     * @return A list of stop words for a specific language.
     */
    abstract public List<String> getWordList();

    /**
     * Retrieves the language for which the stop word list is used.
     * @return The language (or type of list).
     */
    abstract String getLanguage();
}
