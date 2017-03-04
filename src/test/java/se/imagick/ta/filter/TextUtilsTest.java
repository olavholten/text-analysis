package se.imagick.ta.filter;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.stream.IntStream;

/**
 * Created by
 * User: Olav Holten
 * Date: 2017-03-02
 */
public class TextUtilsTest {

    @Test
    public void isAlphaOrSpaceOrSentenceDividerStripsSurplusChars() {

        String text = "flygande bäckasiner sök/=a() hvila på# mjuka t¤uv%o&r.";
        IntStream chars = text.codePoints();
        StringBuilder sb = new StringBuilder();
        chars.filter(TextUtils::isAlphaOrSpaceOrSentenceDivider).forEach(e -> sb.append(Character.toChars(e)));

        // Old Swedish manual type writer test sentence, ensuring all charachters were working :-)
        Assert.assertEquals("flygande bäckasiner söka hvila på mjuka tuvor.", sb.toString());
    }

    @Test
    public void devideSentences() {
        String text = "The all new website was grand. It had all the bells and wistles you could think of. " +
                "There was just one flaw, it had no customers that were willing to pay for it's services.";

        List<String> sentenceList = TextUtils.devideSentences(text);
        Assert.assertEquals(4, sentenceList.size());
    }

    @Test
    public void testDevideSentenceIntoWords() {
        String text = "  This is a sentence to be devided   into words  ";
        List<String> wordList = TextUtils.devideSentenceIntoWords(text);
        Assert.assertEquals(9, wordList.size());
    }

    @Test
    public void testGetAllTerms() {
        String text = "This is a sentence";
        List<List<String>> termList = TextUtils.getAllTerms(text, 3);
        Assert.assertEquals(9, termList.size());

        Assert.assertEquals(1, termList.get(0).size());
        Assert.assertEquals(1, termList.get(1).size());
        Assert.assertEquals(1, termList.get(2).size());
        Assert.assertEquals(1, termList.get(3).size());

        Assert.assertEquals(2, termList.get(4).size());
        Assert.assertEquals(2, termList.get(5).size());
        Assert.assertEquals(2, termList.get(6).size());

        Assert.assertEquals(3, termList.get(7).size());
        Assert.assertEquals(3, termList.get(8).size());
    }

    @Test
    public void getAllTermsAsConcatStrings() {
        String text = "This is a sentence";
        List<String> termList = TextUtils.getAllTermsAsConcatStrings(text, 3);
        Assert.assertEquals(9, termList.size());
    }
}
