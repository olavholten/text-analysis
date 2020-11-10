package se.imagick.ta.tfidf;

import org.junit.Assert;
import org.junit.Test;
import se.imagick.ta.filter.CharacterUtils;
import se.imagick.ta.filter.ParseType;
import se.imagick.ta.language.StopWordsSwedish;

import java.io.ByteArrayInputStream;
import java.io.IOException;

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
public class DocumentTest {

    @Test
    public void newLineBecomesSpace() throws IOException {
        Library library = new Library(ParseType.KEEP_ALL_WORDS);
        library.addStopWordList(new StopWordsSwedish());

        String content = "A day" + CharacterUtils.NEW_LINE + "in paradise!";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(content.getBytes());

        Document document = library.addAndGetNewDocument().addContent(inputStream);
        document.close();

        String cleanContent = document.getContent();
        Assert.assertEquals("A day in paradise! ", cleanContent);
    }

    @Test
    public void devidedWordsAtEndOfLineIsAppended() throws IOException {
        Library library = new Library(ParseType.KEEP_ALL_WORDS);
        library.addStopWordList(new StopWordsSwedish());

        String content = "A day in para- " + CharacterUtils.NEW_LINE + "dise!";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(content.getBytes());

        Document document = library.addAndGetNewDocument().addContent(inputStream);
        document.close();

        String cleanContent = document.getContent();
        Assert.assertEquals("A day in paradise! ", cleanContent);
    }
}
