package se.imagick.ta.language;

/**
 * Created by
 * User: Olav Holten
 * Date: 2017-03-02
 */
public class StopWordGenerator {
    /*
    static void readFile(File textFile, List<String> rowList) throws IOException {
//        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(textFile), "UTF-8"))) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(textFile), "windows-1252"))) {
            String line;
            while ((line = br.readLine()) != null) {
                rowList.add(line);
            }
        }
    }

    static List<String> getRowListFromDirectoryFiles(String rootPath, String subPath) throws Exception {
        List<String> rowList = new ArrayList<>();

        File languageDir = new File(rootPath + subPath);
        File[] textFiles = languageDir.listFiles();

        for (File file : textFiles) {
            readFile(file, rowList);
        }

        return rowList;
    }

    static List<String> aggregateAndSortInFrequency(List<String> wordList) {
        Map<String, WordSortEntry> wordMap = aggregateWords(wordList);

        List<WordSortEntry> sortEntryList = new ArrayList<>(wordMap.values());
        sortEntryList.sort((e1, e2) -> e2.noof.compareTo(e1.noof));
        ArrayList<String> sortedWordList = new ArrayList<>(sortEntryList.size());
        sortEntryList.forEach(e -> sortedWordList.add(e.word));

        double totNoof = wordList.size();
        double noofSoFar = 0;
        int no = 0;

        for (WordSortEntry entry : sortEntryList) {
            if (no < 300) {
                noofSoFar += entry.noof;
                String word = entry.word;
//                String translated = translator.translate(word);
                int procSoFar = (int) (100 * noofSoFar / totNoof);

//                System.out.println(no++ + " : " + procSoFar + "% : " + entry.word + " : " + translated) ;
                System.out.println(no++ + " : " + procSoFar + "% : " + entry.word);
            }
        }

        return sortedWordList;
    }

    private static Map<String, WordSortEntry> aggregateWords(List<String> wordList) {
        Map<String, WordSortEntry> wordMap = new HashMap<>();

        for (String word : wordList) {
            WordSortEntry sortEntry = wordMap.get(word);

            if (sortEntry == null) {
                sortEntry = new WordSortEntry();
                sortEntry.noof = 0L;
                sortEntry.word = word;
                wordMap.getCached(word, sortEntry);
            }

            sortEntry.noof++;
        }
        return wordMap;
    }

*/

}
