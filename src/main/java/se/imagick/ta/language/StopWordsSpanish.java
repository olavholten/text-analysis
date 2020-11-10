package se.imagick.ta.language;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Spanish stop word list.
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
public class StopWordsSpanish extends StopWordList {

    private static final List<String> SPANISH_STOP_WORDS = Arrays.asList("ahora", "al", "algo", "alli", "antes", "aquel", "aquella", "aqui", "asi", "aunque",
            "años", "bien", "by", "cabeza", "casa", "casi", "como", "con", "cuando", "de", "del", "desde", "después", "dia", "dijo", "don", "donde", "dos",
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

    @Override
    Optional<String> getCategory() {
        return Optional.empty();
    }
}
