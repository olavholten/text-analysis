package se.imagick.ta.filter;

import se.imagick.ta.language.StopWordList;
import se.imagick.ta.misc.TermCache;
import se.imagick.ta.tfidf.Term;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by
 * User: Olav Holten
 * Date: 2017-06-08
 */
public class ParseUtils {

    public List<Term> getTermsInDocument(String content) throws IOException {

        return ParseUtils.getTermsInDocument(new ByteArrayInputStream(content.getBytes()));
    }

    public List<Term> getTermsInDocument(File contentFile) throws IOException {
        return ParseUtils.getTermsInDocument(new FileInputStream(contentFile));
    }

    public static List<Term> getTermsInDocument(InputStream contentStream) throws IOException {

        try (BufferedReader reader = getEncodingCorrectReader(contentStream)) {
            List<String> rowList = new ArrayList<>();
            reader.lines().forEach(rowList::add);
            return ParseUtils.parseDocumentRows(rowList);
        }
    }

    public static List<Term> parseDocumentRows(List<String> contentRowList) {
        StringBuilder content = new StringBuilder(20000);
        contentRowList.forEach(line -> content.append(line.trim()).append(" \n"));
        return ParseUtils.parseDocument(content);
    }

    public static List<Term> parseDocument(StringBuilder content) {
        String cleanContent = CharacterUtils.cleanString(content.toString());
        return parseDocument(cleanContent);
    }

    private static List<Term> parseDocument(String cleanContent) {
        TermCache termCache = new TermCache();
        return CharacterUtils.devideSentences(cleanContent).stream()
                .map(str -> ParseUtils.getAllTermsInSentence(str, 1, null, ParseType.KEEP_ALL_WORDS))
                .flatMap(List::stream)
                .map(termCache::getCached) // Releases memory to GC.
                .collect(Collectors.toList());
    }

    /**
     * Devides a sentence into words. All unwanted characters must be taken away before calling this method
     * EG sentence deviders (punctuation marks, commas, semicolon etc).
     *
     * @param sentence The sentence to be divided
     * @return A List of all words in order of appearance in the sentence.
     */
    public static List<String> devideSentenceIntoWords(String sentence) {

        String[] wordArray = sentence.toLowerCase().split(" ");
        return Arrays.stream(wordArray)
                .map(String::trim)
                .filter(w -> !w.replace(CharacterUtils.WORD_DEVIDER, "").trim().isEmpty())
                .collect(Collectors.toList());
    }

    /**
     * Retrieves all terms in a sentence as a list of a list of words.
     *
     * @param sentence               The sentence for which to retrieve the terms.
     * @param maxNoOfWordsInTerms    The max number of words in a term. Eg 3 will retrieve words, be-grams and tri-grams.
     * @param stopWordListCollection Stop word lists.
     * @param parseType              Specifies how the content will be impacted by this stop word list.
     * @return A list of list of words building up the terms found (including duplicates).
     */
    public static List<Term> getAllTermsInSentence(String sentence, int maxNoOfWordsInTerms, List<StopWordList> stopWordListCollection, ParseType parseType) {

        List<String> wordList = devideSentenceIntoWords(sentence);
        int size = wordList.size();
        List<Term> termList = new ArrayList<>();

        for (int noOfWords = 1; noOfWords <= maxNoOfWordsInTerms; noOfWords++) {
            for (int termNo = 0; termNo + noOfWords <= size; termNo++) {

                List<String> termWordList = new ArrayList<>();

                for (int wordNo = termNo; wordNo < termNo + noOfWords; wordNo++) {
                    String word = wordList.get(wordNo);
                    termWordList.add(word);
                }

                stripStopWordsFromTerm(termWordList, stopWordListCollection, parseType);

                if (!termWordList.isEmpty()) {
                    termList.add(new Term(termWordList));
                }
            }
        }

        return termList;
    }

    public static void stripStopWordsFromTerm(List<String> termWordList, List<StopWordList> stopWordListCollection, ParseType parseType) {
        if (stopWordListCollection != null) {

            if (parseType == ParseType.REMOVE_ALL_STOP_WORDS) {
                // Remove all stop words from all stop word lists (if multiple).
                stopWordListCollection.forEach(swl -> termWordList.removeIf(swl::isStopWord));

            } else if (parseType == ParseType.REMOVE_TERMS_WITH_ONLY_STOP_WORDS) {

                stopWordListCollection.stream()
                        .filter(list -> !termWordList.isEmpty()) // no need to continue if list is already empty.
                        .filter(list -> termWordList.stream().allMatch(list::isStopWord))
                        .findAny()
                        .ifPresent(list -> termWordList.clear());
            }
        }
    }

    /*
     * Retrieves all terms in a sentence as a list of words.
     *
     * @param sentence           The sentence for which to retrieve the terms.
     * @param maxNoOfWordsInTerm The max number of words in a term. Eg 3 will retrieve words, be-grams and tri-grams.
     * @param stopWordListCollection The collection of stop word lists that will be used.
     * @return A list of list of words building up the terms found (including duplicates).
     */
    public static List<String> getAllTermsAsConcatStrings(String sentence, int maxNoOfWordsInTerm, List<StopWordList> stopWordListCollection) {

        List<Term> allTerms = getAllTermsInSentence(sentence, maxNoOfWordsInTerm, stopWordListCollection, null);
        return allTerms.stream()
                .map(Term::getJoinedTerm)
                .collect(Collectors.toList());
    }

    private static BufferedReader getEncodingCorrectReader(InputStream contentStream) throws IOException {
        return new BufferedReader(EncodingCorrectReader.getReader(contentStream).orElseThrow(() -> new IOException("Error reading file")));
    }


}
