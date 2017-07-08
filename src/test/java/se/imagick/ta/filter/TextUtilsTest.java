package se.imagick.ta.filter;

import org.junit.Assert;
import org.junit.Test;
import se.imagick.ta.tfidf.Term;

import java.util.List;
import java.util.stream.IntStream;

/**
 * The MIT License (MIT)
 * Copyright (c) 2017 Olav Holten
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
public class TextUtilsTest {

    @Test
    public void isAlphaOrSpaceOrSentenceDividerStripsSurplusChars() {

        String text = "flygande bäckasiner sök/=a() hvila på# mjuka t¤uv%o&r.";
        IntStream chars = text.codePoints();
        StringBuilder sb = new StringBuilder();
        chars.filter(CharacterUtils::isAlphaOrDashOrSpaceOrSentenceDivider).forEach(e -> sb.append(Character.toChars(e)));

        // Old Swedish manual type writer test sentence, ensuring all charachters were working :-)
        Assert.assertEquals("flygande bäckasiner söka hvila på mjuka tuvor.", sb.toString());
    }

    @Test
    public void devideSentences() {
        String text = "The all new website was grand. It had all the bells and wistles you could think of. " +
                "There was just one flaw, it had no customers that were willing to pay for it's services.";

        List<String> sentenceList = CharacterUtils.devideSentences(text);
        Assert.assertEquals(4, sentenceList.size());
    }

    @Test
    public void testDevideSentenceIntoWords() {
        String text = "  This is a sentence to be devided   into words  ";
        List<String> wordList = ParseUtils.devideSentenceIntoWords(text);
        Assert.assertEquals(9, wordList.size());
    }

    @Test
    public void testGetAllTerms() {
        String text = "This is a sentence";
        List<Term> termList = ParseUtils.getAllTermsInSentence(text, 3, null, null);
        Assert.assertEquals(9, termList.size());

        Assert.assertEquals("this", termList.get(0).getJoinedTerm());
        Assert.assertEquals("is", termList.get(1).toString());
        Assert.assertEquals("a", termList.get(2).toString());
        Assert.assertEquals("sentence", termList.get(3).toString());

        Assert.assertEquals("this is", termList.get(4).toString());
        Assert.assertEquals("is a", termList.get(5).toString());
        Assert.assertEquals("a sentence", termList.get(6).toString());

        Assert.assertEquals("this is a", termList.get(7).toString());
        Assert.assertEquals("is a sentence", termList.get(8).toString());
    }

    @Test
    public void getAllTermsAsConcatStrings() {
        String text = "This is a sentence";
        List<String> termList = ParseUtils.getAllTermsAsConcatStrings(text, 3, null);
        Assert.assertEquals(9, termList.size());

        Assert.assertEquals("this", termList.get(0));
        Assert.assertEquals("is", termList.get(1));
        Assert.assertEquals("a", termList.get(2));
        Assert.assertEquals("sentence", termList.get(3));

        Assert.assertEquals("this is", termList.get(4));
        Assert.assertEquals("is a", termList.get(5));
        Assert.assertEquals("a sentence", termList.get(6));

        Assert.assertEquals("this is a", termList.get(7));
        Assert.assertEquals("is a sentence", termList.get(8));
    }

    @Test
    public void detectsSpecialDeviders() {
        String content = "This(is)a very odd[sentence]to test.";
        StringBuilder sb = new StringBuilder();
        content.codePoints()
                .map(c -> (CharacterUtils.isSpecialDevider(c)) ? ' ' : c)
                .forEach(e -> sb.append(Character.toChars(e)));

        Assert.assertEquals("This is a very odd sentence to test.", sb.toString());
    }


}
