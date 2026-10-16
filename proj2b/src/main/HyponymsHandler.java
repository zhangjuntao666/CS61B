package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import wordnet.WordNet;

import java.util.*;

public class HyponymsHandler extends NgordnetQueryHandler {
    private WordNet wn;

    /**constructor*/
    public HyponymsHandler (WordNet wn) {
        this.wn = wn;
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
        return returnWord.toString();
    }
}
