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
 * TODO retrie percentage of words sorted out by the stop word list for confidence evaluation.
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
