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
                .addContent("The lazy dog ")
                .addContent("jumps over the quick brown fox. The end!")
                .close();

        document2.addContent("Why does this document have neither name nor hedline? Because ")
                .addContent("it's test data, and such does not have to have that at all! That is the truth")
                .close();

        List<TF> tfList = document2.getTF(50); // Retrieves the 50 most common words with stop word list
        List<TFIDF> TFIDFList = document1.getTFIDC(50); // Retrieves the words with the 50 highest TF-IDC scores.

        TFIDFList.forEach(System.out::println);

        Assert.assertEquals(0d, TFIDFList.get(TFIDFList.size() - 1).getTfIdf(), 0);
        int commonWordSize = TFIDFList.stream().filter(e -> e.getTfIdf() == 0d).collect(Collectors.toList()).size();
        Assert.assertEquals(6, commonWordSize);
    }

    @Test
    public void smallHappyPath() {

        Library library = new Library(ParseType.REMOVE_ALL_STOP_WORDS);
        library.addStopWordList(new StopWordsSwedish());

        Document document1 = library.addAndGetNewDocument();
        Document document2 = library.addAndGetNewDocument();

        document1.addContent("a cat is sitting on your face")
                .close();
        document2.addContent("a sitting bull is on your side")
                .close();

        List<TF> tfList = document2.getTF(50); // Retrieves the 50 most common words with stop word list
        List<TFIDF> TFIDFList = document1.getTFIDC(50); // Retrieves the words with the 50 highest TF-IDC scores.

        TFIDFList.forEach(System.out::println);

        Assert.assertEquals(0d, TFIDFList.get(TFIDFList.size() - 1).getTfIdf(), 0);
        int valueWordSize = TFIDFList.stream().filter(e -> e.getTfIdf() != 0d).collect(Collectors.toList()).size();
        Assert.assertEquals(12, valueWordSize);
    }
}
