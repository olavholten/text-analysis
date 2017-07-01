package se.imagick.ta.tfidf;

import org.junit.Assert;
import org.junit.Test;
import se.imagick.ta.filter.ParseType;
import se.imagick.ta.language.StopWordsEnglish;

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
        library.addStopWordList(new StopWordsEnglish());

        Document document1 = library.addAndGetNewDocument();
        Document document2 = library.addAndGetNewDocument();

        document1.setName("All chars")
                .setHeadline("All the chars in the alphabet is in this document")
                .addContent("The lazy dog ")
                .addContent("jumps over the quick brown fox. The end!")
                .close();

        document2.addContent("Why does this document have neither name nor hedline? Because ")
                .addContent("it's test data, and such does not have to have that at all! That is the truth.")
                .addContent("Well, my truth anyway. The end.")
                .close();

        List<TF> tfList = document2.getTF(50); // Retrieves the 50 most common words with stop word list
        List<TFIDF> TFIDFList = document1.getTFIDC(50); // Retrieves the words with the 50 highest TF-IDC scores.
//        TFIDFList.forEach(System.out::println);
        Assert.assertEquals(0d, TFIDFList.get(TFIDFList.size() - 1).getTfIdf(), 0);
        List<TFIDF> commonTerms = TFIDFList.stream().filter(e -> e.getTfIdf() == 0d).collect(Collectors.toList());
        commonTerms.forEach(System.out::println);
        int commonWordSize = commonTerms.size();
        Assert.assertEquals(4, commonWordSize);
    }

    @Test
    public void smallHappyPath() {

        Library library = new Library(ParseType.REMOVE_TERMS_WITH_ONLY_STOP_WORDS);
        library.addStopWordList(new StopWordsEnglish());

        Document document1 = library.addAndGetNewDocument();
        Document document2 = library.addAndGetNewDocument();

        document1.addContent("Your cat is sitting on down").close();
        document2.addContent("Sitting Bull is on your side").close();

        List<TF> tfList = document2.getTF(50);
        List<TFIDF> TFIDFList = document1.getTFIDC(50);

        System.out.println("-----");
        TFIDFList.forEach(System.out::println);

        Assert.assertEquals(0d, TFIDFList.get(TFIDFList.size() - 1).getTfIdf(), 0); // Common words last (TF-IDF = 0).
        int valueWordSize = TFIDFList.stream().filter(e -> e.getTfIdf() != 0d).collect(Collectors.toList()).size();
        Assert.assertEquals(11, valueWordSize);
    }
}
