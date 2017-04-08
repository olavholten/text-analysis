package se.imagick.ta.tfidc;

import se.imagick.ta.filter.EncodingCorrectReader;
import se.imagick.ta.filter.TextUtils;
import se.imagick.ta.misc.TermCache;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Olav Holten on 2017-01-29
 */
public class Document {

    private String name;
    private String headline;
    private final StringBuilder content = new StringBuilder();
    private final Library library;
    private final TermCache termCache;
    private final Map<Term, TF> termFrequencies = new HashMap<>(2048);
    private double totalTermCount;
    private boolean isClosed;

    public Document(Library library) {
        this.library = library;
        this.termCache = this.library.getTermCache();
    }

    /**
     * Adds a name to this document (EG file name, URL, book title etc).
     * @param name The name.
     * @return This document.
     */
    public Document setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Adds a headline to the document.
     * @param headline The headline to be added.
     * @return This document.
     */
    public Document setHeadline(String headline) {
        this.headline = headline;
        return this;
    }

    /**
     * Appends text to this document. Repeat until done.
     *
     * @param text The text to be append.
     * @return this document.
     */
    public Document setText(String text) {

        if (isClosed) {
            throw new IllegalStateException("The document is closed!");
        }

        content.append(text);
        return this;
    }

    /**
     * Sets the text to the content of the specified text resource (file), and then closes the document for further input.
     * @param textResource An InputStream containing text.
     * @return This document.
     * @throws IOException If the resource could not be read.
     */
    public Document setText(InputStream textResource) throws IOException {

        Optional<Reader> reader = EncodingCorrectReader.getReader(textResource);
        BufferedReader bufferedReader = new BufferedReader(reader.orElse(null));
        StringBuilder sb = new StringBuilder();
        String line;

        while ((line = bufferedReader.readLine()) != null) {
            sb.append(line);
        };

        setText(sb.toString());
        close();
        return this;
    }

    /**
     * Sets the text to the content of the specified text resource (file), and then closes the document for further input.
     * @param textResournce A file containing text.
     * @return This document.
     * @throws IOException If the resource could not be read.
     */
    public Document setText(File textResournce) throws IOException {
        setText(new FileInputStream(textResournce));
        close();
        return this;
    }

    /**
     * Closes the document for input and starts processing the text.
     * @return This document.
     */
    public Document close() {
        if (!isClosed) {
            isClosed = true;
            decomposeAndAddTerms(this.name);
            decomposeAndAddTerms(this.headline);
            decomposeAndAddTerms(this.content.toString());
        }

        return this;
    }

    /**
     * Processes this document after closing.
     * @param content The content.
     */
    private void decomposeAndAddTerms(String content) {

        if (content != null) {
            String cleanContent = TextUtils.cleanString(content);
            List<Term> termList = TextUtils.devideSentences(cleanContent).stream()
                    .filter(e -> !e.isEmpty())
                    .map(str -> TextUtils.getAllTerms(str, 3, library.getStopWordLists(), library.getParseType()))
                    .flatMap(List::stream)
                    .map(termCache::getCached) // Releases memory to GC.
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

    public List<TFIDF> getTFIDC(int maxNoOfTerms) {

        if (!isClosed) {
            throw new IllegalStateException("The document is not closed!");
        }

        double totNoOfDocs = this.library.getNoOfDocumentsInLibrary();

        List<TFIDF> tfIdcList = termFrequencies.values().stream()
                .map(tf -> new TFIDF(tf.getTerm(), tf.getFrequency(), this.library.getNoOfDocumentsWithTerm(tf.getTerm()), totNoOfDocs))
                .sorted(this::compareTFIDC)
                .limit(maxNoOfTerms)
                .collect(Collectors.toList());

        return tfIdcList;
    }

    double getTotalTermCount() {
        return totalTermCount;
    }

    private void addTerm(Term term) {

        totalTermCount++;
        TF tf = termFrequencies.computeIfAbsent(term, this::createTermFrequency);
        tf.increaseTermOccurencyByOne();
    }

    private TF createTermFrequency(Term term) {
        term = library.addTerm(term, this); // Term String instance reuse.
        return new TF(term, this);
    }

    private int compareTF(TF tf1, TF tf2) {
        double delta = tf2.getFrequency() - tf1.getFrequency();
        return (delta < 0 ? -1 : (delta == 0) ? 0 : 1);
    }

    private int compareTFIDC(TFIDF TFIDF1, TFIDF TFIDF2) {
        double delta = TFIDF2.getTfIdf() - TFIDF1.getTfIdf();
        return (delta < 0 ? -1 : (delta == 0) ? 0 : 1);
    }
}
