package se.imagick.ta.tfidc;

import java.util.List;

/**
 * Created by Olav Holten on 2017-01-29
 */
public class Document {

    private String name;
    private String headline;
    private StringBuilder content = new StringBuilder();

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


    }

    public List<TF> getTF(int maxNoOfTerms) {
        return null;
    }

    public List<TFIDC> getTFIDC(int maxNoOfTerms) {
        return null;
    }
}
