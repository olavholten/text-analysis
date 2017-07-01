# imagick-TextAnalysis

TF-IDF (Term Frequency - Inverse Document Frequency) finds terms that are used more frequently in one document compared to other documents.
It also removes terms that are present in all of the other documents (stop words etc), 
making this algorithm more suitable for many small documents (such as articles) than a few large ones (such as books). 
The larger the documents, the greater the number and the more versatile the subjects that those documents cover must be for the algorithm to give relevant output.
The documents will be cleaned before being used. com.ibm.icu.text.CharsetDetector is used for encoding detection.

##Usage is simple:

<pre>
// Create a library and set none or several stop word lists.
<br/>
Library library = new Library(3, ParseType.REMOVE_TERMS_WITH_ONLY_STOP_WORDS); // Retrieves a document that will parse data for tri-grams (groups of three words).
library.addStopWordList(new StopWordsEnglish());
<br/>
// Create documents (here we create them all at once, but one at a time would be the preferred way
<br/>
Document document1 = library.addAndGetNewDocument();
Document document2 = library.addAndGetNewDocument();
<br/>
// Document data must be cleaned from strange characters etc but still contain scentence delimiters (punctuation mark, exclamation marks, questions marks etc).
<br/>
document1.setName("All chars").setHeadline("All chars in the alphabet").addContent("The lazy dog ").addData("jumps over the quick brown fox. The end!").close(); 
document2.addContent(new FileInputStream("/mypath/mydocument.txt")).close();
<br/>
List&lt;TF&gt; tfList = document1.getTF(50, true); // Retrieves the 50 most common words with stop word list 
List&lt;TFIDC&gt; TFIDFList = document1.getTFIDC(50); // Retrieves the words with the 50 highest TF-IDC scores.
</pre>

Just add the following dependency in your pom-file:

    <dependency>
        <groupId>se.imagick</groupId>
        <artifactId>TA</artifactId>
        <version>1.0</version>
    </dependency>
    
Currently, this project is not added to Maven central so you must build it yourself using
<pre>
mvn clean install. 
</pre>
It will be added soon.

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
