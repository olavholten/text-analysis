package se.imagick.ta.language;

import java.util.Arrays;
import java.util.List;

/**
 * Created by john on 2017-02-04
 */
public class StopWordsSpanish implements StopWords{

     private static final List<String> SPANISH_STOP_WORDS = Arrays.asList("ahora", "al", "algo", "alli", "antes", "aquel", "aquella", "aqui", "asi", "aunque",
             "años", "bien", "by", "cabeza", "casa",  "casi", "como", "con", "cuando", "de", "del", "desde", "después", "dia", "dijo", "don", "donde", "dos",
             "el", "ella", "en", "entonces", "entre", "era", "eran", "es", "esta", "estaba", "este", "esto", "está", "fin", "fue", "gran", "ha", "habia",
             "habian", "hacer", "hacia", "han", "hasta", "hay", "he", "hombre", "hombres", "iba", "la", "las", "le", "les", "lo", "los", "luego", "mano", "me",
             "menos", "mi", "mientras", "mismo", "mucho", "mujer", "mundo", "muy", "más", "nada", "ni", "no", "noche", "nos", "nota", "ojos", "otra", "otro",
             "otros", "para", "pero", "poco", "por", "porque", "pues", "que", "quien", "qué", "rey", "se", "ser", "señor", "si", "siempre", "sin", "sobre",
             "son", "su", "sus", "sólo", "tal", "también", "tan", "tanto", "te", "tenia", "tiempo", "tiene", "todo", "toda", "todas", "todos", "tres", "un",
             "una", "uno", "usted", "veces", "ver", "vez", "vida", "voz", "ya", "yo", "él");

    @Override
    public List<String> getWordList() {
        return SPANISH_STOP_WORDS;
    }

    @Override
    public String getLanguage() {
        return "es";
    }
}
