package se.imagick.ta.tfidc;

import org.junit.Test;
import se.imagick.ta.filter.ParseType;
import se.imagick.ta.language.StopWordsSwedish;

import java.util.List;

/**
 * Integration test.
 * Created by john on 2017-02-03
 */
public class TFIDFIT {

    @Test
    public void happyPath() {

        Library library = new Library(ParseType.REMOVE_TERMS_WITH_ONLY_STOP_WORDS);
        library.addStopWordList(new StopWordsSwedish());

        Document document1 = library.addAndGetNewDocument();
        Document document2 = library.addAndGetNewDocument();

// Document data must be cleaned from strange characters etc but must still contain
// scentence delimiters (punctuation mark, exclamation marks, questions marks etc).

        document1.setName("All chars")
                .setHeadline("All chars in the alphabet")
                .addText("The lazy dog ")
                .addText("jumps over the quick brown fox. The end!")
                .close();

        document2.addText("Why does this document have neither name nor hedline? Because it's test data, and such does not have that!")
                .close();

        List<TF> tfList = document2.getTF(50); // Retrieves the 50 most common words with stop word list
        List<TFIDC> tfidcList = document1.getTFIDC(50); // Retrieves the words with the 50 highest TF-IDC scores.

        tfList.forEach(System.out::println);
    }
}
