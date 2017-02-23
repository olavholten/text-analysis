package se.imagick.ta.language;

import org.junit.Assert;
import org.junit.Test;
import se.imagick.ta.language.LanguageDetector;
import se.imagick.ta.language.StopWordsEnglish;
import se.imagick.ta.language.StopWordsSpanish;
import se.imagick.ta.language.StopWordsSwedish;

/**
 * Created by Olav Holten on 2017-02-05
 */
public class LanguageDetectorTest {
    @Test
    public void detect() {
        String document = "La tuitera que alucina con la respuesta de Rajoy a la postal navideña de su padre";
        String language = LanguageDetector.detect(document,
                new StopWordsEnglish(),
                new StopWordsSwedish(),
                new StopWordsSpanish());

        Assert.assertEquals("es", language);
    }
}
