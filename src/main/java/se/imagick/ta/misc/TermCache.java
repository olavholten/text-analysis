package se.imagick.ta.misc;

import se.imagick.ta.tfidf.Term;

import java.util.HashMap;
import java.util.Map;

/**
 * Just a simple utility class for reuse of String instances rather than
 * having millions of copies of the same words.
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
public class TermCache {
    private Map<Term, Term> termCache = new HashMap<>(150000);

    /**
     * Takes a term and retrieves a cached version of it (to save memory).
     * If a cached version of the term is not saved in the cache,
     * the specified instance will be added to the cache.
     *
     * Usage:
     * <pre>
     * TermCache termCache = new TermCache();
     * List termList = getTerms().stream()
     *     .map(termCache::getCached)
     *     .collect(Collectors.toList());
     * </pre>
     *
     * @param term The term that wich we search a cached instance of.
     * @return A cached term.
     */
    public Term getCached(Term term) {
        return termCache.computeIfAbsent(term, t -> term);
    }
}
