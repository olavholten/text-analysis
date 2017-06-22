package se.imagick.ta.filter;

import com.ibm.icu.text.CharsetDetector;
import com.ibm.icu.text.CharsetMatch;

import java.io.*;
import java.util.Optional;

/**
 * User: Olav.Holten
 * Date: 2017-02-09
 * Time: 15:48
 */
public class EncodingCorrectReader {

    /**
     * Retrieves a java.io.Reader, using the detected file encoding for the inputStream.
     * @param filePathAndName The file path and name.
     * @return A java.io.Reader using the (hopefully) correct character encoding (courtesy of IBM).
     * @throws IOException If the stream fails.
     */
    public static Optional<Reader> getReader(String filePathAndName) throws IOException {
        return getReader(new FileInputStream(filePathAndName));
    }


    /**
     * Retrieves a java.io.Reader, using the detected file encoding for the inputStream.
     * @param inputStream The content as an java.io.InputStream.
     * @return An Optional containing a java.io.Reader using the (hopefully) correct
     * character encoding (courtesy of IBM).
     * @throws IOException If the stream fails.
     */
    public static Optional<Reader> getReader(InputStream inputStream) throws IOException {

        BufferedInputStream bis = new BufferedInputStream(inputStream);
        CharsetDetector cd = new CharsetDetector();
        cd.setText(bis);
        CharsetMatch cm = cd.detect();
        Reader reader = (cm == null) ? null : cm.getReader();

        return Optional.ofNullable(reader);
    }


    /**
     * Retrieves a java.io.Reader, using the detected file encoding for the inputStream.
     * @param textFile The content as an java.io.File.
     * @return An Optional containing a java.io.Reader using the (hopefully) correct
     * character encoding (courtesy of IBM).
     * @throws IOException If the stream fails.
     */
    public static Optional<Reader> getReader(File textFile) throws IOException {

        return getReader(new FileInputStream(textFile));
    }

    private static BufferedReader getEncodingCorrectReader(File textFile) throws IOException {
        return new BufferedReader(EncodingCorrectReader.getReader(textFile).orElseThrow(() -> new IOException("Error reading file")));
    }

    /**
     * Retrieves a com.ibm.icu.text.CharsetMatch, using the detected file encoding for the inputStream.
     * Use if you need to know detected language etc.
     * @param inputStream The content as an java.io.InputStream.
     * @return A com.ibm.icu.text.CharsetMatch using the (hopefully) correct file encoding (courtesy of IBM).
     * @throws IOException If the stream fails.
     */
    public static Optional<CharsetMatch> getCharsetMatch(InputStream inputStream) throws IOException {

        BufferedInputStream bis = new BufferedInputStream(inputStream);
        CharsetDetector cd = new CharsetDetector();
        cd.setText(bis);
        CharsetMatch cm = cd.detect();

        return Optional.ofNullable(cm);
    }
}
