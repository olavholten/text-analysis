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
 * Created by
 * User: Olav Holten
 * Date: 2017-03-02
 */
public class StopWordGenerator {

    public static void main(String[] args) throws Exception {
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
            BufferedReader bufferedReader = new BufferedReader(EncodingCorrectReader.getReader(textFile).orElseThrow(() -> new IOException("Error reading file")));
            ParseUtils.addContentRowsToList(bufferedReader, rowList);
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

        public Term term;
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
