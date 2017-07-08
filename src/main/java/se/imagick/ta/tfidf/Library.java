package se.imagick.ta.tfidf;

import se.imagick.ta.filter.ParseType;
import se.imagick.ta.language.StopWordList;
import se.imagick.ta.misc.TermCache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Holder of all Document instances.
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
public class Library {

    private final int maxNoOfWordsInTerms;
    private final ParseType parseType;
    private final List<StopWordList> stopWordListCollection = new ArrayList<>();
    private final List<Document> documentList = new ArrayList<>(1024); // Memory is cheap, cores are expensive.
    private final Map<Term, DocumentHolder> termMap = new HashMap<>();
    private final TermCache termCache = new TermCache();

    public Library(ParseType parseType) {
        this(3, parseType);
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

    public List<StopWordList> getStopWordLists() {
        return this.stopWordListCollection;
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

    TermCache getTermCache() {
        return termCache;
    }

    public ParseType getParseType() {
        return parseType;
    }

    public int getMaxNoOfWordsInTerms() {
        return maxNoOfWordsInTerms;
    }
}
