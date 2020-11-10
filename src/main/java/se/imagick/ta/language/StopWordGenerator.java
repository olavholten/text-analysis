package se.imagick.ta.language;

import se.imagick.ta.filter.EncodingCorrectReader;
import se.imagick.ta.filter.ParseUtils;
import se.imagick.ta.tfidf.Term;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Creates a stop word list using several documents. To have some confidence in a stop word list
 * there must be at least 1-2 million words, preferably from many different sources for texts
 * (books, articles, authors, publishers etc). If only a few books are used, the names of the
 * main characters will show up as frequent words, and the author's choice of words will highly
 * impact the result.
 *
 * To make a subject specific stop word list, a generic one should be run
 * on the texts first to filter out the usual suspects. The sources must be carefully selected
 * to be only in the category you want to find. And please remember that stop word for a certain
 * subject, such as medical or fashion articles, changes quickly over time. They will have to be
 * updated regularly to be effective.
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
public class StopWordGenerator {

    public static void main(String[] args) throws Exception {
        // Example code.
        String rootPath = "src/main/resources/books";
        String subPath = "en";
        List<Term> sortedTerms = getStopWords(rootPath, subPath);
        sortedTerms.forEach(term -> System.out.print("\"" + term.getJoinedTerm() + "\", "));
    }

    public static List<Term> getStopWords(String rootPath, String subPath) throws Exception {
        List<String> rowList = getRowListFromDirectoryFiles(rootPath, subPath);
        List<Term> allTerms = ParseUtils.parseDocumentRows(rowList);

        return sortTermsInFrequencyOrder(allTerms);
    }

    public static void printoutTermsInFrequencyOrder(String rootPath, String subPath) throws Exception {
        List<String> rowList = getRowListFromDirectoryFiles(rootPath, subPath);
        List<Term> allTerms = ParseUtils.parseDocumentRows(rowList);

        double totNoof = allTerms.size();
        double noofSoFar = 0;
        int no = 0;

        List<TermFrequencySortEntry> sortEntries = aggregateWords(allTerms).values().stream()
                .sorted()
                .limit(300)
                .collect(Collectors.toList());

        for (TermFrequencySortEntry entry : sortEntries) {
            noofSoFar += entry.noof;
            int procSoFar = (int) (100 * noofSoFar / totNoof);
            System.out.println(no++ + " : " + procSoFar + "% : " + entry.term);
        }
    }

    private static List<String> getRowListFromDirectoryFiles(String rootPath, String subPath) throws Exception {

        File languageDir = new File(rootPath, subPath);
        File[] textFiles = languageDir.listFiles();
        List<String> rowList = new ArrayList<>();

        for (File textFile : textFiles) {
            BufferedReader bufferedReader = new BufferedReader(EncodingCorrectReader.getReader(textFile)
                    .orElseThrow(() -> new IOException("Error reading file")));
            bufferedReader.lines().forEach(rowList::add);
        }

        return rowList;
    }

    private static List<Term> sortTermsInFrequencyOrder(List<Term> wordList) {

        return aggregateWords(wordList).values().stream()
                .sorted()
                .map(entry -> entry.term)
                .collect(Collectors.toList());
    }

    private static Map<Term, TermFrequencySortEntry> aggregateWords(List<Term> termList) {

        Map<Term, TermFrequencySortEntry> TermFrequencyMap = new HashMap<>();
        termList.forEach(term -> TermFrequencyMap.computeIfAbsent(term, TermFrequencySortEntry::new).noof++);
        return TermFrequencyMap;
    }

    private static class TermFrequencySortEntry implements Comparable<TermFrequencySortEntry> {

        Term term;
        Long noof;

        // Convenience method for map.computeIfAbsent.
        TermFrequencySortEntry(Term term) {
            this.term = term;
            this.noof = 0L;
        }

        @Override
        public int compareTo(TermFrequencySortEntry entry) {
            return (int) (entry.noof - this.noof);
        }
    }
}
