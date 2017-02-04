package se.imagick.ta.tfidc;

import se.imagick.ta.filter.Decomposer;
import se.imagick.ta.misc.Counter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Olav Holten on 2017-01-29
 */
public class Document {

    private String name;
    private String headline;
    private StringBuilder content = new StringBuilder();
    private Library library;
    private List<TF> termFrequencies = new ArrayList<>(2048);
    private Counter totDocumentTermsOccurencyCount;

    public Document(Library library) {
        this.library = library;
    }

    public Document setName(String name) {
        this.name = name;
        return this;
    }

    public Document setHeadline(String headline) {
        this.headline = headline;
        return this;
    }

    public Document addText(String text) {
        content.append(text);
        return this;
    }

    public void close() {
        // start processing the text.

        // get tot noOf words and calculate tf. Set the TF  tot term count (common instance for all TF in document).
        List<String> sentenceList = Decomposer.documentToSentences(content.toString());
        List<String> collect = sentenceList.stream().map(Decomposer::scentenceToTerms).flatMap(Collection::stream).collect(Collectors.toList());
        collect.forEach(w -> library.addWord(w, this)); // TODO Fix the String caching.
    }

    public List<TF> getTF(int maxNoOfTerms) {
        return this.termFrequencies.subList(0, maxNoOfTerms);
    }

    public List<TFIDC> getTFIDC(int maxNoOfTerms) {
        return null;
    }
}
