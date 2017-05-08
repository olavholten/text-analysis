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
    private final Map<Term, TF> termFrequencies = new HashMap<>(15000);
    private double totalTermCount;
    private boolean isClosed;

    public Document(Library library) {
        this.library = library;
        this.termCache = this.library.getTermCache();
    }

    /**
     * Adds a name to this document (EG file name, URL, book title etc).
     *
     * @param name The name.
     * @return This document.
     */
    public Document setName(String name) {
        validateOpen();
        this.name = name;
        return this;
    }

    /**
     * Adds a headline to the document.
     *
     * @param headline The headline to be added.
     * @return This document.
     */

    public Document setHeadline(String headline) {
        validateOpen();
        this.headline = headline;
        return this;
    }

    /**
     * Appends text to this document. Repeat until done.
     *
     * @param text The text to be append.
     * @return this document.
     */
    public Document addContent(String text) {
        validateOpen();
        content.append(text);
        return this;
    }

    /**
     * Sets the text to the content of the specified text resource (file), and then closes the document for further input.
     *
     * @param textResource An InputStream containing text.
     * @return This document.
     * @throws IOException If the resource could not be read.
     */
    public Document addContent(InputStream textResource) throws IOException {

        validateOpen();
        Optional<Reader> reader = EncodingCorrectReader.getReader(textResource);
        BufferedReader bufferedReader = new BufferedReader(reader.orElse(null));
        StringBuilder sb = new StringBuilder();
        String line;

        while ((line = bufferedReader.readLine()) != null) {
            sb.append(TextUtils.addSpaceToEndOfLine(line.trim()));
        }

        addContent(sb.toString());

        return this;
    }

    /**
     * Sets the text to the content of the specified text resource (file), and then closes the document for further input.
     *
     * @param textResournce A file containing text.
     * @return This document.
     * @throws IOException If the resource could not be read.
     */
    public Document addContent(File textResournce) throws IOException {
        validateOpen();
        addContent(new FileInputStream(textResournce));
        return this;
    }

    /**
     * Closes the document for input and starts processing the text.
     * Ignores subsequent calls.
     *
     * @return This document.
     */
    public Document close() {

        validateOpenAndSetClosed();
        decomposeAndAddTerms(this.name);
        decomposeAndAddTerms(this.headline);
        decomposeAndAddTerms(this.content.toString());

        return this;
    }

    public List<TF> getTF(int maxNoOfTerms) {
        validateClosed();
        return this.termFrequencies.values().stream().sorted(this::compareTF).limit(maxNoOfTerms).collect(Collectors.toList());
    }

    public List<TFIDF> getTFIDC(int maxNoOfTerms) {

        validateClosed();
        double totNoOfDocs = this.library.getNoOfDocumentsInLibrary();

        return termFrequencies.values().stream()
                .map(tf -> new TFIDF(tf.getTerm(), tf.getFrequency(), this.library.getNoOfDocumentsWithTerm(tf.getTerm()), totNoOfDocs))
                .sorted(this::compareTFIDC)
                .limit(maxNoOfTerms)
                .collect(Collectors.toList());
    }

    public String getContent() {
        return this.content.toString();
    }


    public double getTotalTermCount() {
        validateClosed();
        return totalTermCount;
    }

    private void validateOpen() {
        synchronized (this) {
            if (isClosed) {
                throw new IllegalStateException("The document is closed!");
            }
        }
    }

    private void validateClosed() {
        synchronized (this) {
            if (!isClosed) {
                throw new IllegalStateException("The document is not closed!");
            }
        }
    }

    private void validateOpenAndSetClosed() {
        synchronized (this) {
            if (isClosed) {
                throw new IllegalStateException("The document is closed!");
            }

            isClosed = true;
        }
    }

    /**
     * Processes this document after closing.
     *
     * @param content The content.
     */
    private void decomposeAndAddTerms(String content) {

        if (content != null) {
            String cleanContent = TextUtils.cleanString(content);
            List<Term> termList = TextUtils.devideSentences(cleanContent).stream()
                    .filter(e -> !e.isEmpty())
                    .map(str -> TextUtils.getAllTerms(str, 1, library.getStopWordLists(), library.getParseType()))
                    .flatMap(List::stream)
                    .map(termCache::getCached) // Releases memory to GC.
                    .collect(Collectors.toList());

            termList.forEach(this::addTerm);
        }
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
