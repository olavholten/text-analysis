package se.imagick.ta.filter;

import se.imagick.ta.language.StopWordsSwedish;
import se.imagick.ta.tfidc.Document;
import se.imagick.ta.tfidc.Library;
import se.imagick.ta.tfidc.TFIDF;

import java.io.*;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static se.imagick.ta.filter.EncodingCorrectReader.getReader;

/**
 * Created by
 * User: Olav Holten
 * Date: 2017-03-23
 */
public class EncodingCorrectReaderTest {

    public static void main(String[] args) throws IOException {

        Library library = new Library(ParseType.REMOVE_TERMS_WITH_ONLY_STOP_WORDS);
        library.addStopWordList(new StopWordsSwedish());
        File[] books = new File("src/main/resources/books/se").listFiles();
        Document document = null;

        for (File book : books) {
            Optional<Reader> reader = getReader(new FileInputStream(book));
            BufferedReader bufferedReader = new BufferedReader(reader.orElse(null));

            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            };

            document = library.addAndGetNewDocument();
            document.addText(sb.toString());
            document.close();
        }

        List<TFIDF> tfidcList = document.getTFIDC(10000);

//        tfidcList.removeIf(e -> e.getTerm().getNoOfWords() < 3);

        tfidcList.forEach(System.out::println);
    }
}