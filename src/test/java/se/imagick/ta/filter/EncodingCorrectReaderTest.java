package se.imagick.ta.filter;

import se.imagick.ta.language.StopWordsSwedish;
import se.imagick.ta.tfidc.Document;
import se.imagick.ta.tfidc.Library;
import se.imagick.ta.tfidc.TFIDF;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by
 * User: Olav Holten
 * Date: 2017-03-23
 */
public class EncodingCorrectReaderTest {

    public static void main(String[] args) throws IOException {

        Library library = new Library(ParseType.REMOVE_ALL_STOP_WORDS);
        library.addStopWordList(new StopWordsSwedish());
        Document document = null;

        for (File bookFile : new File("src/main/resources/books/se").listFiles()) {
            document = library.addAndGetNewDocument().setText(bookFile);
            document.close();
        }

        List<TFIDF> tfidcList = document.getTFIDC(10000);
        tfidcList.forEach(System.out::println);
    }
}