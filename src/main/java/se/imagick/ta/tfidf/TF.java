package se.imagick.ta.tfidf;

/**
 * Created by Olav Holten on 2017-02-03
 */
public class TF {

    private Term term;
    private Document document;
    private double noOfOccurenciesOfTerm;
    private Double frequency;

    public TF(Term term, Document document) {
        this.term = term;
        this.document = document;
        this.frequency = null;
        this.noOfOccurenciesOfTerm = 0;
    }

    public Term getTerm() {
        return term;
    }

    public double getFrequency() {
        // This method is used for sorting, so prevention of multiple calculations is needed.
        frequency =  (frequency == null)?noOfOccurenciesOfTerm / document.getTotalTermCount():frequency;
        return frequency;
    }

    public void increaseTermOccurencyByOne() {
        this.noOfOccurenciesOfTerm++;
        this.frequency = null;
    }

    public String toString() {
        return "Term: " + term + ", freq: " + getFrequency();
    }
}
