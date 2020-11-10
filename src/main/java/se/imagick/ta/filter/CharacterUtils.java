package se.imagick.ta.filter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * Util methods for cleaning texts from unwanted characters etc.
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
@SuppressWarnings("WeakerAccess")
public class CharacterUtils {

    public static final char NEW_LINE = 10;
    public static final String WORD_DEVIDER = "-";
    private static final char[] SENTENCE_DIVIDER_CHARS = "!?,.;:".toCharArray();
    public static final String REGEX_PREFIX = "\\";

    public static boolean isAlphaOrDashOrSpaceOrSentenceDivider(int chararcter) {
        return chararcter == 32 || chararcter == 45 || isAlpha(chararcter) || isSentenceDivider(chararcter);
    }

    public static boolean isAlphaOrSpace(int chararcter) {
        return chararcter == 32 || isAlpha(chararcter);
    }

    public static boolean isSentenceDivider(int character) {

        for (char devider : SENTENCE_DIVIDER_CHARS) { // Faster than indexOf
            if (character == devider) {
                return true;
            }
        }

        return false;
    }

    public static boolean isAlpha(int character) {
        return (character >= 65 && character <= 90)
                || (character >= 97 && character <= 122)
                || (character >= 192 && character <= 214)
                || (character >= 216 && character <= 122)
                || (character >= 216 && character <= 246)
                || (character >= 248 && character <= 450)
                ;
    }

    public static List<String> devideSentences(String text) {

        List<String> sentenceList = new ArrayList<>();
        sentenceList.add(text);

        for (char ch : SENTENCE_DIVIDER_CHARS) {
            sentenceList = split(sentenceList, ch);
        }

        sentenceList.removeIf(str -> str.trim().isEmpty());

        return sentenceList;
    }

    public static List<String> split(List<String> fragments, char ch) {
        return fragments.stream()
                .flatMap(str -> Arrays.stream(str.split(REGEX_PREFIX + ch)))
                .map(String::trim)
                .filter(str -> !str.isEmpty())
                .collect(Collectors.toList());
    }

    public static String addSpaceToEndOfLine(String line) {

        line = line.trim();
        return (line.endsWith(WORD_DEVIDER)) ? line.substring(0, line.length() - 1) : line + " ";
    }

    public static String join(List<String> wordList) {
        StringJoiner sj = new StringJoiner(" ");
        wordList.forEach(sj::add);
        return sj.toString().trim();
    }

    public static String cleanString(String content) {

        StringBuilder sb = new StringBuilder();
        content.codePoints()
                .map(c -> (isSpecialDevider(c)) ? ' ' : c)
                .filter(CharacterUtils::isAlphaOrDashOrSpaceOrSentenceDivider)
                .forEach(e -> sb.append(Character.toChars(e)));

        return sb.toString();
    }

    public static boolean isSpecialDevider(int c) {
        return c == '&'
                || c == '('
                || c == ')'
                || c == '['
                || c == ']'
                ;
    }

}
