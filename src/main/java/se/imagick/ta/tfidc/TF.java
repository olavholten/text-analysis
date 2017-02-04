package se.imagick.ta.tfidc;

import se.imagick.ta.misc.Counter;

/**
 * Created by Olav Holten on 2017-02-03
 */
public class TF {

    private String term;
    private Counter documentTermsCounter; // Common counter for all words/terms in the document
    private long noOfOccurenciesOfTerm;

    public TF(String term, Counter documentTermsCount){
        this.term = term;
        this.documentTermsCounter = documentTermsCount;
    }

    public String getTerm() {
        return term;
    }

    public long getFrequency() {
        return noOfOccurenciesOfTerm / documentTermsCounter.getCount();
    }

    public void increaseTermOccurencyByOne() {
        this.noOfOccurenciesOfTerm++;
    }
}
