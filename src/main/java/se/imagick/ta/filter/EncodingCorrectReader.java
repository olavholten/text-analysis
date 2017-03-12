package se.imagick.ta.filter;

import com.ibm.icu.text.CharsetDetector;
import com.ibm.icu.text.CharsetMatch;

import java.io.*;
import java.util.Optional;

/**
 * User: Olav.Holten
 * Date: 2017-02-09
 * Time: 15:48
 *
 * <dependency>
 *   <groupId>com.ibm.icu</groupId>
 *   <artifactId>icu4j</artifactId>
 *   <version>58.2</version>
 * </dependency>
 */
public class EncodingCorrectReader {


    public static Optional<Reader> getReader(String fileName) throws IOException {
        return getReader(new FileInputStream(fileName));
    }


    public static Optional<Reader> getReader(InputStream is) throws IOException {

        BufferedInputStream bis = new BufferedInputStream(is);
        CharsetDetector cd = new CharsetDetector();
        cd.setText(bis);
        CharsetMatch cm = cd.detect();
        Reader reader = (cm == null)?null:cm.getReader();

        return Optional.ofNullable(reader);
    }

    public static void main(String[] args) throws IOException {
        Optional<Reader> reader  = getReader("src/main/resources/Ada.txt");
        BufferedReader bufferedReader = new BufferedReader(reader.orElse(null));

        String line;
        while((line = bufferedReader.readLine()) != null) {
            System.out.println(line);
        };

    }
}