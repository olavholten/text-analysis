package se.imagick.ta.misc;

import se.imagick.ta.tfidc.Term;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by
 * User: Olav Holten
 * Date: 2017-03-07
 */
public class TermCache {
    private Map<Term, Term> termCache = new HashMap<>();

    /**
     * Takes a term and retrieves a cached version of it (to save memory).
     * If a cached version of the term is not saved in the cache,
     * the specified instance will be added to the cache.
     *
     * @return A cached term.
     */
    public Term getCached(Term term) {
        return termCache.computeIfAbsent(term, t -> term);
    }
}
