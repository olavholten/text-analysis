package se.imagick.ta.tfidf;

/**
 * TF, Term Frequency. The total number of times a Term occurs in the library.
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
public class TF {

    private Term term;
    private Document document;
    private double noOfOccurenciesOfTerm;
    private Double frequency;

    public TF(Term term, Document document) {
        this.term = term;
        this.document = document;
        this.frequency = null;
        this.noOfOccurenciesOfTerm = 0;
    }

    public Term getTerm() {
        return term;
    }

    public double getFrequency() {
        // This method is used for sorting, so prevention of multiple calculations is needed.
        frequency = (frequency == null) ? noOfOccurenciesOfTerm / document.getTotalTermCount() : frequency;
        return frequency;
    }

    public void increaseTermOccurencyByOne() {
        this.noOfOccurenciesOfTerm++;
        this.frequency = null;
    }

    public String toString() {
        return "Term: " + term + ", freq: " + getFrequency();
    }
}
