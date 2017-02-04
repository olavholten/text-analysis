package se.imagick.ta.tfidc;

import se.imagick.ta.filter.Decomposer;

import java.util.Arrays;
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
        List<String> sentenceList = Decomposer.toSentences(content.toString());
        List<String> collect = sentenceList.stream().map(Decomposer::scentenceToWords).flatMap(Collection::stream).collect(Collectors.toList());
        collect.forEach(w -> library.addWord(w, this)); // TODO Fix the String caching.
    }

    public List<TF> getTF(int maxNoOfTerms) {
        return null;
    }

    public List<TFIDC> getTFIDC(int maxNoOfTerms) {
        return null;
    }
}
