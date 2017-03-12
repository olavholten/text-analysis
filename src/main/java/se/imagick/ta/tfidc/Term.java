package se.imagick.ta.tfidc;

import se.imagick.ta.filter.TextUtils;

import java.util.List;

/**
 * Created by
 * User: Olav Holten
 * Date: 2017-03-07
 */
public class Term {
    private String joinedTerm;
    private List<String> wordList;
    private Integer hashCode;

    public Term(List<String> wordList) {
        this.wordList = wordList;
        this.joinedTerm = TextUtils.join(this.wordList);
        this.hashCode = this.wordList.hashCode();
    }

    public String getJoinedTerm() {
        return joinedTerm;
    }

    public List<String> getWordList() {
        return wordList;
    }

    public boolean equals(Object o) {
        return o instanceof Term && this.hashCode() == o.hashCode();
    }

    public int hashCode() {
        return hashCode;
    }

    public String toString() {
        return joinedTerm;
    }
}
