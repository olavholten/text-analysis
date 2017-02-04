package se.imagick.ta.tfidc;

/**
 * Created by john on 2017-02-03
 */
public class TF {
    private String term;
    private long frequency;

    public String getTerm() {
        return term;
    }

    public TF setTerm(String term) {
        this.term = term;
        return this;
    }

    public long getFrequency() {
        return frequency;
    }

    public TF setFrequency(long frequency) {
        this.frequency = frequency;
        return this;
    }
}
