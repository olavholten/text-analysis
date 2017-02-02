# imagick-TextAnalysis

TF-IDC (Term Frequency - Inverse Document Frequency) finds terms that are used more frequently in one document compared to other documents.
It also removes terms that are present in all of the other documents (stop words etc), 
making this algorithm more suitable for many small documents (such as articles) than a few large ones (such as books). 
The larger the documents, the greater the number and the more versatile the subjects that those documents cover must be for the algorithm to give relevant output.
The documents must be cleaned before being used. 

##Usage is simple:

<pre>
Library library = new Library(3, ParseType.REMOVE_TERMS_WITH_ONLY_STOP_WORDS); // Retrieves a document that will parse data for tri-grams (groups of three words).
library.addStopWordList("en", new String[]{"and","or","if","what"}); // Must be clean words without extra characters such as punctuation marcs etc.

Document document1 = library.addAndGetNewDocument();
Document document2 = library.addAndGetNewDocument();

// Document data must be cleaned from strange characters etc but still contain scentence delimiters (punctuation mark, exclamation marks, questions marks etc).
document1.setName("All chars").setHeadline("All chars in the alphabet").addData("The lazy dog ").addData("jumps over the quick brown fox. The end!"); 
document2.addData("Why does this document has neither name nor hedline? Because it's test data!");

List<TF> tfList = document1.getTF(50, true); // Retrieves the 50 most common words with stop word list 
List<TFIDC> tfidcList = document1.getTFIDC(50); // Retrieves the words with the 50 highest TF-IDC scores.
</pre>

Just add the following dependency in your pom-file:

    <dependency>
        <groupId>se.imagick</groupId>
        <artifactId>test-analysis</artifactId>
        <version>1.0</version>
    </dependency>

## Licence:

The MIT License (MIT)
Copyright (c) 2017 Olav Holten

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
