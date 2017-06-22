package se.imagick.ta.language;

import se.imagick.ta.filter.EncodingCorrectReader;
import se.imagick.ta.filter.ParseUtils;
import se.imagick.ta.tfidf.Term;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by
 * User: Olav Holten
 * Date: 2017-03-02
 */
public class StopWordGenerator {

    // TODO Move moste functionality to CharacterUtils.

    public static void main(String[] args) throws Exception {
        String rootPath = "src/main/resources/books";
        String subPath = "en";
        List<String> rowList = ParseUtils.getRowListFromDirectoryFiles(rootPath, subPath);
        List<Term> allTerms = ParseUtils.parseDocumentRows(rowList);
        List<Term> sortedTerms = aggregateAndSortInFrequency(allTerms);

        sortedTerms.forEach(term -> System.out.print("\"" + term.getJoinedTerm() + "\", "));
    }



    static List<Term> aggregateAndSortInFrequency(List<Term> wordList) {
        Map<Term, WordSortEntry> wordMap = aggregateWords(wordList);

        List<WordSortEntry> sortEntryList = new ArrayList<>(wordMap.values());
        sortEntryList.sort((e1, e2) -> e2.noof.compareTo(e1.noof));
        ArrayList<Term> sortedWordList = new ArrayList<>(sortEntryList.size());
        sortEntryList.forEach(term -> sortedWordList.add(term.term));

        double totNoof = wordList.size();
        double noofSoFar = 0;
        int no = 0;

        for (WordSortEntry entry : sortEntryList) {
            if (no < 300) {
                noofSoFar += entry.noof;
                Term term = entry.term;
//                String translated = translator.translate(word);
                int procSoFar = (int) (100 * noofSoFar / totNoof);

//                System.out.println(no++ + " : " + procSoFar + "% : " + entry.word + " : " + translated) ;
                System.out.println(no++ + " : " + procSoFar + "% : " + entry.term);
            }
        }

        return sortedWordList;
    }

    private static Map<Term, WordSortEntry> aggregateWords(List<Term> termList) {
        Map<Term, WordSortEntry> wordMap = new HashMap<>();

        for (Term term : termList) {
            WordSortEntry sortEntry = wordMap.computeIfAbsent(term, WordSortEntry::new);
            sortEntry.noof++;
        }

        return wordMap;
    }

    private static class WordSortEntry {

        public Term term;
        public Long noof;

        // Convenience method for map.computeIfAbsent.
        WordSortEntry(Term term) {
            this.term = term;
            this.noof = 0L;
        }
    }
}
