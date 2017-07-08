package se.imagick.ta.misc;

import org.junit.Assert;
import org.junit.Test;
import se.imagick.ta.tfidf.Term;

import java.util.Arrays;

/**
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
public class TermCacheTest {

    private static String[] words = {"Tower", "of", "Terror"};

    @Test
    public void getSameTermInstanceWhenNonExistingInCache() {
        TermCache cache = new TermCache();
        Term term = new Term(Arrays.asList(words));
        Term cachedTerm = cache.getCached(term);

        Assert.assertTrue(term == cachedTerm);
        Assert.assertEquals(term, cachedTerm);
    }

    @Test
    public void getEqualCachedTermWhenExistingInCache() {
        TermCache cache = new TermCache();
        Term term1 = new Term(Arrays.asList(words));
        cache.getCached(term1);
        Term term2 = new Term(Arrays.asList(words));
        Term cachedTerm = cache.getCached(term2);

        Assert.assertTrue(term2 != cachedTerm); // Not the same instance
        Assert.assertEquals(term2, cachedTerm); // But does have the same contence.
        Assert.assertEquals(term1, term2);
        Assert.assertTrue(term1 == cachedTerm);
    }
}