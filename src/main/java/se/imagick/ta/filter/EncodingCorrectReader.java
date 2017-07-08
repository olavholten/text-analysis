package se.imagick.ta.filter;

import com.ibm.icu.text.CharsetDetector;
import com.ibm.icu.text.CharsetMatch;

import java.io.*;
import java.util.Optional;

/**
 * Uses the IBM CharsetDetector to open a text document using the correct encoding.
 *
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
