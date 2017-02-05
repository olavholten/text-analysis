package se.imagick.ta.tfidc;

import se.imagick.ta.misc.Decomposer;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Olav Holten on 2017-01-29
 */
public class Document {

    private String name;
    private String headline;
    private StringBuilder content = new StringBuilder();
    private Library library;
    private Map<String, TF> termFrequencies = new HashMap<>(2048);
    private double totalTermCount;
    private boolean isClosed;

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

        if (isClosed) {
            throw new IllegalStateException("The document is closed!");
        }

        content.append(text);
        return this;
    }

    public void close() {
        // start processing the text.
        isClosed = true;
        decomposeAndAddTerms(this.name);
        decomposeAndAddTerms(this.headline);
        decomposeAndAddTerms(this.content.toString());
    }

    private void decomposeAndAddTerms(String content) {

        if (content != null) {
            List<String> sentenceList = Decomposer.documentToSentences(content);
            List<String> termList = sentenceList.stream()
                    .map(String::trim)
                    .filter(e -> !e.isEmpty())
                    .map(Decomposer::scentenceToTerms)
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList());

            termList.forEach(this::addTerm);
        }

    }

    public List<TF> getTF(int maxNoOfTerms) {

        if (!isClosed) {
            throw new IllegalStateException("The document is not closed!");
        }

        return this.termFrequencies.values().stream().sorted(this::compareTF).limit(maxNoOfTerms).collect(Collectors.toList());
    }

    public List<TFIDC> getTFIDC(int maxNoOfTerms) {

        if (!isClosed) {
            throw new IllegalStateException("The document is not closed!");
        }

        double totNoOfDocs = this.library.getNoOfDocumentsInLibrary();

        List<TFIDC> tfIdcList = termFrequencies.values().stream()
                .map(tf -> new TFIDC(tf.getTerm(), tf.getFrequency(), this.library.getNoOfDocumentsWithTerm(tf.getTerm()), totNoOfDocs))
                .sorted(this::compareTFIDC)
                .limit(maxNoOfTerms)
                .collect(Collectors.toList());

        return tfIdcList;
    }

    double getTotalTermCount() {
        return totalTermCount;
    }

    private void addTerm(String term) {

        totalTermCount++;
        TF tf = termFrequencies.get(term);

        if (tf == null) {
            term = library.addTerm(term, this); // Term String instance reuse.
            termFrequencies.put(term, new TF(term, this));
        } else {
            tf.increaseTermOccurencyByOne();
        }
    }

    private int compareTF(TF tf1, TF tf2) {
        double delta = tf2.getFrequency() - tf1.getFrequency();
        return (delta < 0 ? -1 : (delta == 0) ? 0 : 1);
    }

    private int compareTFIDC(TFIDC tfidc1, TFIDC tfidc2) {
        double delta = tfidc1.getTfIdc() - tfidc2.getTfIdc();
        return (delta < 0 ? -1 : (delta == 0) ? 0 : 1);
    }
}
