package se.imagick.ta.tfidc;

/**
 * Created by john on 2017-02-03
 */
public class TFIDC {

    private Term term;
    private double tfIdc;

    public TFIDC(Term term, double frequency, double noOfDocsWithTerm, double totNoOfDocs) {
        this.term = term;
        this.tfIdc = frequency * Math.log(totNoOfDocs / noOfDocsWithTerm);
    }

    public Term getTerm() {
        return term;
    }

    public double getTfIdc() {
        return tfIdc;
    }

    public String toString() {
        return "Term: " + term + ", tfIdc: " + getTfIdc();
    }
}
