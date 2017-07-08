package se.imagick.ta.language;

import java.util.Arrays;
import java.util.List;

/**
 * Swedish stop word list.
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
public class StopWordsSwedish extends StopWordList {

    private static final List<String> SWEDISH_STOP_WORDS = Arrays.asList("aldrig", "all", "alla", "allt", "andra", "andre", "att", "av", "bara", "blev", "bli",
            "blir", "de", "dem", "den", "denna", "dessa", "det", "detta", "dig", "dom", "du", "där", "då", "efter", "ej", "eller", "en", "er", "ett", "fem",
            "fick", "fram", "från", "fyra", "få", "får", "för", "genom", "gick", "gjorde", "gjorde", "gjort", "gå", "gång", "går", "gått", "gör", "göra", "ha",
            "hade", "han", "hans", "har", "hel", "hela", "henne", "hennes", "hon", "honom", "hur", "hvarje", "här", "i", "icke", "in", "inga", "ingen", "inget",
            "inte", "ja", "jag", "ju", "kan", "kan", "kom", "komma", "kommer", "kommit", "kunde", "kunna", "kunnat", "kunnat", "kände", "känna", "känner",
            "känt", "man", "med", "men", "mig", "min", "mitt", "mot", "mycket", "måste", "ned", "ner", "ni", "nio", "nog", "nu", "när", "någon", "något",
            "några", "nåt", "och", "också", "om", "oss", "på", "redan", "sa", "sade", "sagt", "satt", "se", "sedan", "sig", "sin", "sina", "sitt", "sitta",
            "sitter", "sju", "själv", "ska", "skall", "skulle", "som", "stod", "stå", "står", "stått", "suttit", "säga", "säga", "säger", "så", "såg", "till",
            "tio", "tog", "tre", "två", "ty", "under", "upp", "ur", "ut", "utan", "vad", "var", "vara", "varit", "velat", "vet", "vi", "vid", "vilja", "vill",
            "ville", "väl", "än", "ännu", "är", "år", "åt", "åtta", "över");

    @Override
    public List<String> getWordList() {

        return SWEDISH_STOP_WORDS;
    }

    @Override
    public String getLanguage() {
        return "sv";
    }
}
