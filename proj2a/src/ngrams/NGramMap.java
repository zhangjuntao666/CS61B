package ngrams;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import edu.princeton.cs.algs4.In;

import static ngrams.TimeSeries.MAX_YEAR;
import static ngrams.TimeSeries.MIN_YEAR;

/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 *
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {

    // TODO: Add any necessary static/instance variables.
    private Map<String, TimeSeries> data; //用来存每个单词的历史数据
    private TimeSeries total;
    /**
     * Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME.
     */
    public NGramMap(String wordsFilename, String countsFilename) {
        // TODO: Fill in this constructor. See the "NGramMap Tips" section of the spec for help.
        data = new HashMap<>(); //查找更快
        store1(wordsFilename);
        total = new TimeSeries(); //存每年的总词数
        store2(countsFilename);
    }

    /*将word存到data里*/
    private void store1(String wordsFilename) {
        In in = new In(wordsFilename);

        while (!in.isEmpty()) {
            String nextLine = in.readLine();
            String[] splitLine = nextLine.split("\t");
            String word = splitLine[0];
            int year = Integer.parseInt(splitLine[1]);
            double num = Double.parseDouble(splitLine[2]);

            if (!data.containsKey(word)) {
                data.put(word, new TimeSeries());
            }
            data.get(word).put(year, num);
        }
    }

    /*将每年总数存到时间序列total里*/
    private void store2(String countsFilename) {
        In in  = new In(countsFilename);

        while (!in.isEmpty()) {
            String nextLine = in.readLine();
            String[] splitLine = nextLine.split(",");
            int year = Integer.parseInt(splitLine[0]);
            double num = Double.parseDouble(splitLine[1]);

            total.put(year, num);
        }
    }

    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other
     * words, changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy". If the word is not in the data files,
     * returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        // TODO: Fill in this method.
        if (data.containsKey(word)) {
            TimeSeries tsCopy = new TimeSeries(data.get(word), startYear, endYear);
            return tsCopy;
        } else {
            return new TimeSeries();
        }
    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy, not a link to this
     * NGramMap's TimeSeries. In other words, changes made to the object returned by this function
     * should not also affect the NGramMap. This is also known as a "defensive copy". If the word
     * is not in the data files, returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word) {
        // TODO: Fill in this method.
        if (data.containsKey(word)) {
            TimeSeries tsCopy = new TimeSeries();
            for (int key : data.get(word).keySet()) {
                tsCopy.put(key, data.get(word).get(key));
            }
            return tsCopy;
        } else {
            return new TimeSeries();
        }
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     */
    public TimeSeries totalCountHistory() {
        // TODO: Fill in this method.
        TimeSeries totalCopy = new TimeSeries();
        for (int key : total.keySet()) {
            totalCopy.put(key, total.get(key));
        }
        return totalCopy;
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        // TODO: Fill in this method.
        TimeSeries weightTs = new TimeSeries();
        if (data.containsKey(word)) {
            TimeSeries original = data.get(word);
            for (int year = startYear; year <= endYear; year ++) {
                if (original.containsKey(year) && total.containsKey(year)) {
                    double frequency = original.get(year) / total.get(year);
                    weightTs.put(year, frequency);
                }
            }
            return weightTs;
        } else {
            return new TimeSeries();
        }
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to all
     * words recorded in that year. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word) {
        // TODO: Fill in this method.
        TimeSeries weightTS = new TimeSeries();
        if (data.containsKey(word)) {
            TimeSeries original = data.get(word);
            for (int year : original.keySet()) {
                if (total.containsKey(year)) {
                    double frequency = original.get(year) / total.get(year);
                    weightTS.put(year, frequency);
                }
            }
            return weightTS;
        } else {
            return new TimeSeries();
        }
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS between STARTYEAR and
     * ENDYEAR, inclusive of both ends. If a word does not exist in this time frame, ignore it
     * rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words,
                                          int startYear, int endYear) {
        // TODO: Fill in this method.
        TimeSeries sum = new TimeSeries();
        for (String key : words) {
            sum = sum.plus(weightHistory(key, startYear, endYear));
        }
        return sum;
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS. If a word does not
     * exist in this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        // TODO: Fill in this method.
        TimeSeries sum = new TimeSeries();
        for (String key : words) {
            sum = sum.plus(weightHistory(key));
        }
        return sum;
    }

    // TODO: Add any private helper methods.
    // TODO: Remove all TODO comments before submitting.
}
