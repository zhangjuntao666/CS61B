package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import ngrams.TimeSeries;
import org.knowm.xchart.XYChart;
import plotting.Plotter;

import java.util.ArrayList;
import java.util.List;

public class HistoryHandler extends NgordnetQueryHandler {
    private NGramMap ngm;

    /*constructor to receive ngm from main*/
    public HistoryHandler (NGramMap ngm) {
        this.ngm = ngm;
    }

    @Override
    public String handle(NgordnetQuery q) {
        ArrayList<TimeSeries> lts = new ArrayList<>(); //数据
        ArrayList<String> labels = new ArrayList<>(); //标签

        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();

        for (String wordString : words) {
            String[] splitWords = wordString.split(",");

            for (String raWord : splitWords) {
                String word = raWord.trim();
                labels.add(word);
                lts.add(ngm.weightHistory(word, startYear, endYear));
            }
        }


        XYChart chart = Plotter.generateTimeSeriesChart(labels, lts);
        String encodedImage = Plotter.encodeChartAsString(chart);
        return encodedImage;
    }
}
