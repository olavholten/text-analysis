package se.imagick.ta.language;

import se.imagick.ta.filter.ParseType;
import se.imagick.ta.filter.TextUtils;
import se.imagick.ta.misc.TermCache;
import se.imagick.ta.tfidf.Term;

import java.io.*;
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

    public static void main(String [] args) throws Exception {
        String rootPath = "src/main/resources/books";
        String subPath = "en";
        List<String> rowList = getRowListFromDirectoryFiles(rootPath, subPath);
        List<Term> allTerms = parseDocumentRows(rowList);
        List<Term> sortedTerms = aggregateAndSortInFrequency(allTerms);

        sortedTerms.forEach(term -> System.out.print("\"" + term.getJoinedTerm() + "\", "));
    }

    static List<Term> parseDocumentRows(List<String> contentList) {
        TermCache termCache = new TermCache();
        StringBuilder content = new StringBuilder(20000);
        contentList.forEach(line -> content.append(line).append(" "));
        String cleanContent = TextUtils.cleanString(content.toString());

        return TextUtils.devideSentences(cleanContent).stream()
                .map(str -> TextUtils.getAllTerms(str, 1, null, ParseType.KEEP_ALL_WORDS))
                .flatMap(List::stream)
                .map(termCache::getCached) // Releases memory to GC.
                .collect(Collectors.toList());
    }

    static List<String> readFile(File textFile, List<String> rowList) throws IOException {
//        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(textFile), "UTF-8"))) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(textFile), "windows-1252"))) {
            String line;
            while ((line = br.readLine()) != null) {
                rowList.add(line);
            }
        }

        return rowList;
    }

    static List<String> getRowListFromDirectoryFiles(String rootPath, String subPath) throws Exception {
        List<String> rowList = new ArrayList<>();

        File languageDir = new File(rootPath, subPath);
        File[] textFiles = languageDir.listFiles();

        for (File file : textFiles) {
            readFile(file, rowList);
        }

        return rowList;
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
