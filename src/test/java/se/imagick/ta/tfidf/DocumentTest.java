package se.imagick.ta.tfidf;

import org.junit.Assert;
import org.junit.Test;
import se.imagick.ta.filter.ParseType;
import se.imagick.ta.filter.TextUtils;
import se.imagick.ta.language.StopWordsSwedish;

import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * Created by Olav Holten on 2017-02-02
 */
public class DocumentTest {

    @Test
    public void newLineBecomesSpace() throws IOException {
        Library library = new Library(ParseType.KEEP_ALL_WORDS);
        library.addStopWordList(new StopWordsSwedish());

        String content = "A day" + TextUtils.NEW_LINE + "in paradise!";
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

        String content = "A day in para- " + TextUtils.NEW_LINE + "dise!";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(content.getBytes());

        Document document = library.addAndGetNewDocument().addContent(inputStream);
        document.close();

        String cleanContent = document.getContent();
        Assert.assertEquals("A day in paradise! ", cleanContent);
    }
}
