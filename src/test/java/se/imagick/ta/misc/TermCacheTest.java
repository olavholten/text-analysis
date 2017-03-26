package se.imagick.ta.misc;

import org.junit.Assert;
import org.junit.Test;
import se.imagick.ta.tfidc.Term;

import java.util.Arrays;

/**
 * Created by
 * User: Olav Holten
 * Date: 2017-03-26
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
        Assert.assertEquals(term2, cachedTerm); // Does however have the sam contence.
        Assert.assertEquals(term1, term2);
        Assert.assertTrue(term1 == cachedTerm);
    }


}