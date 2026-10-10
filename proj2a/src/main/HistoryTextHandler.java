package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.TimeSeries;
import ngrams.NGramMap;

import java.util.List;

public class HistoryTextHandler extends NgordnetQueryHandler {
    private NGramMap ngm; //不用初始化，到main方法里再初始化

    /*Constructor用来接受main方法里传入的ngm*/
    public HistoryTextHandler (NGramMap ngm) {
        this.ngm = ngm;
    }

    @Override
    public String handle(NgordnetQuery q) {

        List<String> words = q.words();

        int startYear = q.startYear();
        int endYear = q.endYear();
        StringBuilder response = new StringBuilder();
        for (String wordString : words) {
            String[] splitWords = wordString.split(",");

            for (String raWord : splitWords) {
                String word = raWord.trim();
                TimeSeries ts = ngm.weightHistory(word, startYear, endYear);

                response.append(word).append(": ").append(ts.toString()).append("\n");
            }
        }

        return response.toString();
    }
}
