package se.imagick.ta.language;

import java.util.Arrays;
import java.util.List;

/**
 * English stop word list.
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
