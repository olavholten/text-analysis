package se.imagick.ta.tfidc;

/**
 * Created by Olav Holten on 2017-01-29
 */
public class Library {

    private final int maxNoOfWordsInTerms;
    private final ParseType parseType;

    public Library(int maxNoOfWordsInTerms, ParseType parseType) {

        this.maxNoOfWordsInTerms = maxNoOfWordsInTerms;
        this.parseType = parseType;
    }

    public void addStopWordList(String en, String[] strings) {

    }

    public Document addAndGetNewDocument() {
        return null;
    }
}
