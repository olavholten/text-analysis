package se.imagick.ta.tfidc;

import se.imagick.ta.filter.ParseType;
import se.imagick.ta.language.StopWordList;

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
    private final List<StopWordList> stopWordListCollection = new ArrayList<>();
    private final List<Document> documentList = new ArrayList<>(1024); // Memory is cheap, cores are expensive.
    private final Map<Term, DocumentHolder> termMap = new HashMap<>();

    public Library(ParseType parseType) {
        this(1, parseType);
    }

    private Library(int maxNoOfWordsInTerms, ParseType parseType) { // bi-grams, tri-grams etc not implemented.
        this.maxNoOfWordsInTerms = maxNoOfWordsInTerms;
        this.parseType = parseType;
    }

    public void addStopWordList(StopWordList stopWords) {
        stopWordListCollection.add(stopWords);
    }


    public Document addAndGetNewDocument() {
        Document document = new Document(this);
        documentList.add(document);
        return document;
    }


    public Term addTerm(Term term, Document document) {
        // Returns the same String instance, saving memory.
        return termMap.computeIfAbsent(term, DocumentHolder::new).add(document).term;
    }


    private class DocumentHolder {
        Term term;
        List<Document> documentList = new ArrayList<>();

        public DocumentHolder(Term term) {
            this.term = term;
        }

        public DocumentHolder add(Document document) {
            documentList.add(document);
            return this;
        }
    }

    double getNoOfDocumentsInLibrary() {
        return documentList.size();
    }

    double getNoOfDocumentsWithTerm(Term term) {
        return termMap.get(term).documentList.size();
    }
}
