package wordnet;
import java.util.*;

public class Graph {
    private Map<Integer, List<Integer>> adjList;

    /*constructor*/
    public Graph() {
        this.adjList = new HashMap<>();
    }

    /*填充adjList*/
    public void addEdges (int from, int to) {
        if (!adjList.containsKey(from)) {
            adjList.put(from, new ArrayList<>());
        }
        adjList.get(from).add(to);
        //把to也做成一个key，避免指向null
        if (!adjList.containsKey(to)) {
            adjList.put(to, new ArrayList<>());
        }
    }

    /*返回一个ID指向的IDs*/
    public Set<Integer> neighbours(int from) {
        Set<Integer> returnSet = new HashSet<>();
        if (!adjList.containsKey(from)) {
            return new HashSet<>();
        }
        for (int ID : adjList.get(from)) {
            returnSet.add(ID);
        }
        return returnSet;
    }

    /*利用dfs返回一个包含所有从from开始可以达到的IDs的Set*/
    public Set<Integer> getAllIDsBelow (int from) {
        Set<Integer> visited = new HashSet<>();
        dfs(from, visited);
        return visited;
    }

    private void dfs (int current, Set<Integer> visited) {
        if (visited.contains(current)) {
            return ;
        }
        // preOrder
        visited.add(current);
        for (int neighbour : neighbours(current)) {
            dfs(neighbour, visited);
        }
    }

}
