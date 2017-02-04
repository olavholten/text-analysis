package se.imagick.ta.tfidc;

import se.imagick.ta.filter.ParseType;
import se.imagick.ta.filter.StopWords;

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
    private final List<Document> documentList = new ArrayList<>(1024);
    private final Map<String, Map<Document, Document>> wordList = new HashMap<>(2048);

    public Library(int maxNoOfWordsInTerms, ParseType parseType) {

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

    void addWord(String word, Document document) {
        // TODO Return the String instance used to store the hashmap entry, in order to save memory.
        wordList.computeIfAbsent(word, w -> new HashMap<Document, Document>()).put(document, document);
    }
}
