package se.imagick.ta.tfidc;

/**
 * Created by Olav Holten on 2017-02-03
 */
public class TF {

    private Term term;
    private Document document;
    private double noOfOccurenciesOfTerm;

    public TF(Term term, Document document){
        this.term = term;
        this.document = document;
        this.noOfOccurenciesOfTerm = 1;
    }

    public Term getTerm() {
        return term;
    }

    public double getFrequency() {
        return noOfOccurenciesOfTerm / document.getTotalTermCount();
    }

    public void increaseTermOccurencyByOne() {
        this.noOfOccurenciesOfTerm++;
    }

    public String toString() {
        return "Term: " + term + ", freq: " + getFrequency();
    }
}
