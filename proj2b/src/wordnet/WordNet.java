package wordnet;

import edu.princeton.cs.algs4.In;

import java.util.*;

public class WordNet {
    private Map<Integer, List<String>> synsetMap; //一个ID里可能会有多个单词
    private Map<String, List<Integer>> wordToIDs; //一个单词可能会有多个ID
    private Graph graphHyponyms;

    /**
     * Constructs an WordNet from SYNSETS and HYPONYMS.
     */
    public WordNet(String synsets, String hyponyms) {
        synsetMap = new HashMap<>();
        wordToIDs = new HashMap<>();
        graphHyponyms = new Graph();
        loadSynsets(synsets);
        loadHyponyms(hyponyms);
    }

    /*填充synsetMap和wordToIDs实现双向查询*/
    private void loadSynsets(String synsets) {
        In in = new In(synsets);

        while (!in.isEmpty()) {
            String nextLine = in.readLine();
            String[] splitLine = nextLine.split(",");
            int ID = Integer.parseInt(splitLine[0]);
            List<String> words = List.of(splitLine[1].split(" "));
            //填synsetMap
            synsetMap.put(ID, words);
            //填wordToIDs
            for (String word : words) {
                if (!wordToIDs.containsKey(word)) {
                    wordToIDs.put(word, new ArrayList<>());
                }
                wordToIDs.get(word).add(ID);
            }
        }
    }

    private void loadHyponyms(String hyponyms) {
        In in = new In(hyponyms);

        while (!in.isEmpty()) {
            String nextLine = in.readLine();
            String[] splitLine = nextLine.split(",");
            int ID = Integer.parseInt(splitLine[0]);
            for (int i = 1; i < splitLine.length; i++) {
                graphHyponyms.addEdges(ID, Integer.parseInt(splitLine[i]));
            }
        }
    }

    /*查找一个word的所有ID，遍历所有的ID，
    将每个ID可以达到的ID记录在一个set里。
    再遍历这个set里的所有ID，
    利用synsetMap获取所有的hyponyms，并添加都一个returnSet里*/
    public Set<String> getHyponyms(String word) {
        Set<String> returnSet = new HashSet<>();
        Set<Integer> setID = new HashSet<>();

        if (!wordToIDs.containsKey(word)) {
            return returnSet;
        }
        //查找一个word的所有ID，遍历所有的ID，
        //将每个ID可以达到的ID记录在一个set里。
        for (int ID : wordToIDs.get(word)) {
            setID.addAll(graphHyponyms.getAllIDsBelow(ID));
        }

        //再遍历这个set里的所有ID，
        //利用synsetMap获取所有的hyponyms，并添加都一个returnSet里
        for (int ID : setID) {
            returnSet.addAll(synsetMap.get(ID));
        }
        return returnSet;
    }

}
