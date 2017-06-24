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

    public static List<Term> parseDocumentRows(List<String> contentRowList) {
        StringBuilder content = new StringBuilder(20000);
        contentRowList.forEach(line -> content.append(line).append(" \n"));
        return ParseUtils.parseDocument(content);
    }

    public static List<Term> parseDocument(StringBuilder content) {
        String cleanContent = CharacterUtils.cleanString(content.toString());
        TermCache termCache = new TermCache();
        return CharacterUtils.devideSentences(cleanContent).stream()
                .map(str -> ParseUtils.getAllTermsInSentence(str, 1, null, ParseType.KEEP_ALL_WORDS))
                .flatMap(List::stream)
                .map(termCache::getCached) // Releases memory to GC.
                .collect(Collectors.toList());
    }

    /**
     * Convenience method that reads rows from a BufferedReader and ads them row by row to the given list.
     *
     * @param reader  The buffered reader containing the content.
     * @param rowList The list to wich the content rows will be added.
     * @return The same list submitted to the method.
     * @throws IOException If the Reader fails.
     */
    public static List<String> addContentRowsToList(BufferedReader reader, List<String> rowList) throws IOException {

        String line;
        while ((line = reader.readLine()) != null) {
            rowList.add(line);
        }

        return rowList;
    }

    public List<Term> getWordsInDocument(String content) throws IOException {

        return ParseUtils.getWordsInDocument(new ByteArrayInputStream(content.getBytes()));
    }

    public List<Term> getWordsInDocument(File contentFile) throws IOException {
        return ParseUtils.getWordsInDocument(new FileInputStream(contentFile));
    }

    public static List<Term> getWordsInDocument(InputStream contentStream) throws IOException {

        try (BufferedReader reader = getEncodingCorrectReader(contentStream)) {
            List<String> rowList = ParseUtils.addContentRowsToList(reader, new ArrayList<>());
            return ParseUtils.parseDocumentRows(rowList);
        }
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
                for (StopWordList stopWordList : stopWordListCollection) {
                    termWordList.removeIf(stopWordList::isStopWord);
                }
            } else if (parseType == ParseType.REMOVE_TERMS_WITH_ONLY_STOP_WORDS) {

                boolean isOnlyStopWords = false;
                for (StopWordList stopWordList : stopWordListCollection) {
                    int noOfStopWords = 0;

                    for (String word : termWordList) {
                        if (stopWordList.isStopWord(word))
                            noOfStopWords++;
                    }

                    isOnlyStopWords = (noOfStopWords == termWordList.size()) || isOnlyStopWords;
                }

                if (isOnlyStopWords) {
                    termWordList.clear();
                }
            }
        }
    }

    /**
     * Retrieves all terms in a sentence as a list of a list of words.
     *
     * @param sentence           The sentence for which to retrieve the terms.
     * @param maxNoOfWordsInTerm The max number of words in a term. Eg 3 will retrieve words, be-grams and tri-grams.
     * @return A list of list of words building up the terms found (including duplicates).
     */
    public static List<String> getAllTermsAsConcatStrings(String sentence, int maxNoOfWordsInTerm, List<StopWordList> stopWordListCollection) {

        List<Term> allTerms = getAllTermsInSentence(sentence, maxNoOfWordsInTerm, stopWordListCollection, null);
        return allTerms.stream()
                .map(Term::getJoinedTerm)
                .collect(Collectors.toList());
    }

    public static BufferedReader getEncodingCorrectReader(File textFile) throws IOException {
        return new BufferedReader(EncodingCorrectReader.getReader(textFile).orElseThrow(() -> new IOException("Error reading file")));
    }

    private static BufferedReader getEncodingCorrectReader(InputStream contentStream) throws IOException {
        return new BufferedReader(EncodingCorrectReader.getReader(contentStream).orElseThrow(() -> new IOException("Error reading file")));
    }


}
