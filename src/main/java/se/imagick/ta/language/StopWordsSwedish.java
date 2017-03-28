package se.imagick.ta.language;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Olav Holten on 2017-02-03
 */
public class StopWordsSwedish extends StopWordList {

    private static final List<String> SWEDISH_STOP_WORDS = Arrays.asList("aldrig", "all", "alla", "allt", "andra", "andre", "att", "av", "bara", "blev", "bli",
            "blir", "de", "dem", "den", "denna", "dessa", "det", "detta", "dig", "dom", "du", "där", "då", "efter", "ej", "eller", "en", "er", "ett", "fem",
            "fick", "fram", "från", "fyra", "få", "får", "för", "genom", "gick", "gjorde", "gjorde", "gjort", "gå", "gång", "går", "gått", "gör", "göra", "ha",
            "hade", "han", "hans", "har", "hel", "hela", "henne", "hennes", "hon", "honom", "hur", "hvarje", "här", "i", "icke", "in", "inga", "ingen", "inget",
            "inte", "ja", "jag", "ju", "kan", "kan", "kom", "komma", "kommer", "kommit", "kunde", "kunna", "kunnat", "kunnat", "kände", "känna", "känner",
            "känt", "man", "med", "men", "mig", "min", "mitt", "mot", "mycket", "måste", "ned", "ner", "ni", "nio", "nog", "nu", "när", "någon", "något",
            "några", "nåt", "och", "också", "om", "oss", "på", "redan", "sade", "sagt", "satt", "se", "sedan", "sig", "sin", "sina", "sitt", "sitta", "sitter",
            "sju", "själv", "ska", "skall", "skulle", "som", "stod", "stå", "står", "stått", "suttit", "säga", "säga", "säger", "så", "såg", "till", "tio",
            "tog", "tre", "två", "ty", "under", "upp", "ur", "ut", "utan", "vad", "var", "vara", "varit", "velat", "vet", "vi", "vid", "vilja", "vill", "ville",
            "väl", "än", "ännu", "är", "år", "åt", "åtta", "över");

    @Override
    public List<String> getWordList() {

        return SWEDISH_STOP_WORDS;
    }

    @Override
    public String getLanguage() {
        return "sv";
    }
}
