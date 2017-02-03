package se.imagick.ta.tfidc;

import org.junit.Test;

import java.util.List;

/**
 * Integration test.
 * Created by john on 2017-02-03
 */
public class TfIdfIF {

    @Test
    public void happyPath() {
        Library library = new Library(3, ParseType.REMOVE_TERMS_WITH_ONLY_STOP_WORDS); // Retrieves a document that will parse data for tri-grams (groups of three words).
        library.addStopWordList("en", new String[]{"and","or","if","what"}); // Must be clean words without extra characters such as punctuation marcs etc.

        Document document1 = library.addAndGetNewDocument();
        Document document2 = library.addAndGetNewDocument();

// Document data must be cleaned from strange characters etc but still contain scentence delimiters (punctuation mark, exclamation marks, questions marks etc).

        document1.setName("All chars")
                .setHeadline("All chars in the alphabet")
                .addText("The lazy dog ")
                .addText("jumps over the quick brown fox. The end!");

        document2.addText("Why does this document have neither name nor hedline? Because it's test data!");

        List<TF> tfList = document1.getTF(50); // Retrieves the 50 most common words with stop word list
        List<TFIDC> tfidcList = document1.getTFIDC(50); // Retrieves the words with the 50 highest TF-IDC scores.
    }
}