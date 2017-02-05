package se.imagick.ta.tfidc;

import org.junit.Assert;
import org.junit.Test;
import se.imagick.ta.filter.ParseType;
import se.imagick.ta.language.StopWordsSwedish;

import java.util.List;
import java.util.stream.Collectors;

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

        document1.setName("All chars")
                .setHeadline("All the chars in the alphabet is in this document")
                .addText("The lazy dog ")
                .addText("jumps over the quick brown fox. The end!")
                .close();

        document2.addText("Why does this document have neither name nor hedline? Because ")
                .addText("it's test data, and such does not have to have that at all! That is the truth")
                .close();

        List<TF> tfList = document2.getTF(50); // Retrieves the 50 most common words with stop word list
        List<TFIDC> tfidcList = document1.getTFIDC(50); // Retrieves the words with the 50 highest TF-IDC scores.

        tfidcList.forEach(System.out::println);

        Assert.assertEquals(0d, tfidcList.get(tfidcList.size() - 1).getTfIdc(), 0);
        int stopWordSize = tfidcList.stream().filter(e -> e.getTfIdc() == 0d).collect(Collectors.toList()).size();
        Assert.assertEquals(5, stopWordSize);
    }
}
