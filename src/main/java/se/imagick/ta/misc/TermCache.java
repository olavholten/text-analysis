package se.imagick.ta.misc;

import se.imagick.ta.tfidc.Term;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by
 * User: Olav Holten
 * Date: 2017-03-07
 */
public class TermCache {
    private Map<Term, Term> termCache = new HashMap<>();
    private Map<String, String> wordCache = new HashMap<>();

    /**
     * Puts a term into the cache. If not present it will create a new Term instance using cached words.
     * @return A cached term.
     */
    public Term put(Term term) {
        return termCache.computeIfAbsent(term, this::getNewTermWithCachedWords);
    }

    private Term getNewTermWithCachedWords(Term term) {
        return term; // TODO fix word caching and new Term creation.
    }
}
