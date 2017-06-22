package se.imagick.ta.tfidf;

import se.imagick.ta.filter.CharacterUtils;

import java.util.List;

/**
 * Created by
 * User: Olav Holten
 * Date: 2017-03-07
 */
public class Term {
    private String joinedTerm;
    private int noOfWords;
    private int hashCode;

    public Term(List<String> wordList) {
        this.joinedTerm = CharacterUtils.join(wordList);
        this.noOfWords = wordList.size();
        this.hashCode = this.joinedTerm.hashCode();
    }

    public String getJoinedTerm() {
        return joinedTerm;
    }

    public int getNoOfWords() {
        return noOfWords;
    }

    public boolean equals(Object o) {
        return o instanceof Term && this.hashCode == o.hashCode();
    }

    public int hashCode() {
        return hashCode;
    }

    public String toString() {
        return joinedTerm;
    }
}
