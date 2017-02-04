package se.imagick.ta.misc;

/**
 * Created by Olav Holten on 2017-02-04
 */
public class Counter {
    private long count;

    public void increase() {
        count++;
    }

    public void decrease() {
        count--;
    }

    public long getCount() {
        return count;
    }
}
