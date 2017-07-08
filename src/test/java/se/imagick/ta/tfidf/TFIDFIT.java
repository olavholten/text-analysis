package se.imagick.ta.tfidf;

import org.junit.Assert;
import org.junit.Test;
import se.imagick.ta.filter.ParseType;
import se.imagick.ta.language.StopWordsEnglish;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The MIT License (MIT)
 * Copyright (c) 2017 Olav Holten
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
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
        List<TFIDF> TFIDFList = document1.getTFIDF(50); // Retrieves the words with the 50 highest TF-IDF scores.
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
        List<TFIDF> TFIDFList = document1.getTFIDF(50);

        System.out.println("-----");
        TFIDFList.forEach(System.out::println);

        Assert.assertEquals(0d, TFIDFList.get(TFIDFList.size() - 1).getTfIdf(), 0); // Common words last (TF-IDF = 0).
        int valueWordSize = TFIDFList.stream().filter(e -> e.getTfIdf() != 0d).collect(Collectors.toList()).size();
        Assert.assertEquals(11, valueWordSize);
    }
}
