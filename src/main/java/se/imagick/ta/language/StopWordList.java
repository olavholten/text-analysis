package se.imagick.ta.language;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Abstract super class of all stop word lists.
 * Handles initiation of the list and detection of stop words.
 *
 * The MIT License (MIT)
 * Copyright (c) 2017 Olav Holten
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
public abstract class StopWordList {

    private Map<String, String> sortedWords = new HashMap<>();

    public StopWordList() {
        getWordList().forEach(w -> sortedWords.put(w, w));
    }

    /**
     * Detects whether a word is included in this stop word list.
     *
     * @param word The word beeing examined.
     * @return true if this word is in this list.
     */
    public boolean isStopWord(String word) {
        return sortedWords.containsKey(word);
    }

    /**
     * Retrieves a language specific list of the most commonly used words (stop words).
     *
     * @return A list of stop words for a specific language.
     */
    abstract public List<String> getWordList();

    /**
     * Retrieves the language for which the stop word list is used.
     *
     * @return The language (or type of list).
     */
    abstract String getLanguage();

    /**
     * Category may be used to mark a certain subject that this StopWordList is
     * designed to detect.
     * @return Empty optional if basic stop word list for a certain language,
     * otherwise the name of the category.
     */
    abstract Optional<String> getCategory();
}
