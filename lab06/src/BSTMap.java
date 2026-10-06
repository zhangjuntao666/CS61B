import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    private class Node {
        K key;
        V value;
        Node left, right;

        /*constructor of Node*/
        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private Node root;

    @Override
    public void put(K key, V value) {
        root = putHelper(key, value, root);
    }

    private Node putHelper(K key, V value, Node n) {
        if (n == null) {
            return new Node(key, value);
        }
        int cmp = key.compareTo(n.key);
        if (cmp < 0) {
            n.left = putHelper(key, value, n.left);
        } else if (cmp > 0) {
            n.right = putHelper(key, value, n.right);
        } else {
            n.value = value;
        }
        return n;
    }

    @Override
    public V get(K key) {
        return getHelper(key, root);
    }

    private V getHelper(K key, Node n) {
        if (n == null) {
            return null;
        }
        int cmp = key.compareTo(n.key);
        if (cmp < 0) {
            return getHelper(key, n.left);
        } else if (cmp > 0) {
            return getHelper(key, n.right);
        } else {
            return n.value;
        }
    }

    @Override
    public boolean containsKey(K key) {
        return containsKeyHelper(key, root);
    }

    private boolean containsKeyHelper(K key,Node n) {
        if (n == null) {
            return false;
        }
        int cmp = key.compareTo(n.key);
        if (cmp < 0) {
            return containsKeyHelper(key, n.left);
        } else if (cmp > 0) {
            return containsKeyHelper(key, n.right);
        } else {
            return true;
        }
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public void clear() {

    }

    @Override
    public Set<K> keySet() {
        return Set.of();
    }

    @Override
    public V remove(K key) {
        return null;
    }

    @Override
    public Iterator<K> iterator() {
        return null;
    }
}
