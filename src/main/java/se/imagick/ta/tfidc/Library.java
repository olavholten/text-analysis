package se.imagick.ta.tfidc;

import se.imagick.ta.filter.ParseType;
import se.imagick.ta.language.StopWords;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Olav Holten on 2017-01-29
 */
public class Library {

    private final int maxNoOfWordsInTerms;
    private final ParseType parseType;
    private final List<StopWords> stopWordListCollection = new ArrayList<>();
    private final List<Document> documentList = new ArrayList<>(1024); // Memory is cheap, cores are expensive.
    private final Map<String, DocumentHolder> wordList = new HashMap<>();

    public Library(ParseType parseType) {
        this(1, parseType);
    }

    private Library(int maxNoOfWordsInTerms, ParseType parseType) { // bi-grams, tri-grams etc not implemented.
        this.maxNoOfWordsInTerms = maxNoOfWordsInTerms;
        this.parseType = parseType;
    }

    public void addStopWordList(StopWords stopWords) {
        stopWordListCollection.add(stopWords);
    }


    public Document addAndGetNewDocument() {
        Document document = new Document(this);
        documentList.add(document);
        return document;
    }


    public String addTerm(String term, Document document) {
        // Returns the same String instance, saving memory.
        return wordList.computeIfAbsent(term, t -> new DocumentHolder(t, document)).add(document).term;
    }


    private class DocumentHolder{
        String term;
        List<Document> documentList = new ArrayList<>();

        public DocumentHolder(String term, Document document) {
            this.term = term;
            this.documentList.add(document);
        }

        public DocumentHolder add(Document document) {
            documentList.add(document);
            return this;
        }
    }
}
