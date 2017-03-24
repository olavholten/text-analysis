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
        // Returns the same Term instance, saving memory.
        return termMap.computeIfAbsent(term, DocumentHolder::new).add(document).getTerm();
    }


    private class DocumentHolder {
        private Term term;
        private List<Document> documentList = new ArrayList<>();

        DocumentHolder(Term term) {
            this.term = term;
        }

        DocumentHolder add(Document document) {
            documentList.add(document);
            return this;
        }

        public Term getTerm() {
            return term;
        }

        public void setTerm(Term term) {
            this.term = term;
        }

        public List<Document> getDocumentList() {
            return documentList;
        }

        public DocumentHolder setDocumentList(List<Document> documentList) {
            this.documentList = documentList;
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
