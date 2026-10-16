package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import ngrams.TimeSeries;
import org.eclipse.jetty.server.RequestLog;
import wordnet.WordNet;

import java.util.*;

public class HyponymsHandler extends NgordnetQueryHandler {
    private WordNet wn;
    private NGramMap ngm;
    /**constructor*/
    public HyponymsHandler (WordNet wn, NGramMap ngm) {
        this.wn = wn;
        this.ngm = ngm;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        List<String> query = new ArrayList<>();
        Set<String> returnWord = new TreeSet<>();
        int startYear = q.startYear();
        int endYear = q.endYear();
        int k = q.k();

        for (String wordString : words) {
            String[] splitWords = wordString.split(",");

            for (String raWord : splitWords) {
                String word = raWord.trim();
                query.add(word);
            }
        }

        if (query.isEmpty()) {
            return "[]";
        }

        returnWord.addAll(wn.getHyponyms(query.get(0)));
        for (int i = 1; i < query.size(); i++) {
            returnWord.retainAll(wn.getHyponyms(query.get(i)));
        }

        if (k == 0) {
            return returnWord.toString();
        }

        // wordSum存query中的每个单词在ngm.countHistory(String word, int startYear, int endYear)的总和
        Map<String, Double> wordSum = new TreeMap<>();
        for (String word : returnWord) {
            TimeSeries ts = ngm.countHistory(word, startYear, endYear);
            if (!wordSum.containsKey(word)) {
                wordSum.put(word, 0.0);
            }
            if (!ts.isEmpty()) {
                for (int year : ts.keySet()) {
                    wordSum.put(word, wordSum.get(word) + ts.get(year));
                }
            }
        }

        List<String> returnList = new ArrayList<>();
        for (String word : wordSum.keySet()) {
            if (wordSum.get(word) > 0) {
                returnList.add(word);
            }
        }

        if (returnList.isEmpty()) {
            return "[]";
        }

        wordComparator cmp = new wordComparator(wordSum);
        Collections.sort(returnList, cmp); //按cmp先排一遍

        if (returnList.size() > k) {
            returnList = returnList.subList(0, k);
        }

        Collections.sort(returnList);
        return returnList.toString();

    }

    /*为了让wordSum依照sum排序而写得comp*/
    class wordComparator implements Comparator<String> {
        private Map<String, Double> wordSum;

        public wordComparator (Map<String, Double> wordSum) {
            this.wordSum = wordSum;
        }

        @Override
        public int compare(String o1, String o2) {
            int cmp = Double.compare(wordSum.get(o2), wordSum.get(o1)); //升序
            if (cmp != 0) {
                return cmp;
            }
            return o1.compareTo(o2);
        }
    }
}
