package hashmap;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

/**
 *  A hash table-backed Map implementation.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets; //存储了所有bucket地址的列表
    private int M; //number of buckets
    private int N; //number of items
    private double Factor; //N/M 的上限
    // You should probably define some more!

    /** Constructors */
    public MyHashMap() {
        buckets = new Collection[32];
        M = 16;
        N = 0;
        Factor = 0.75;
    }

    public MyHashMap(int initialCapacity) {
        buckets = new Collection[initialCapacity];
        M = initialCapacity;
        N = 0;
        Factor = 0.75;
    }

    /**
     * MyHashMap constructor that creates a backing array of initialCapacity.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialCapacity initial size of backing array
     * @param loadFactor maximum load factor
     */
    public MyHashMap(int initialCapacity, double loadFactor) {
        buckets = new Collection[initialCapacity];
        M = initialCapacity;
        N = 0;
        Factor = loadFactor;
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *  Note that that this is referring to the hash table bucket itself,
     *  not the hash map itself.
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    /*这里return什么根本不重要，只要是实现Collection的类就可以。
    因为后面方法的实现也只需要用到collection里声明的方法就可以了。
    这样也方便我们后续比较不同数据结构作为bucket的运行效果*/
    protected Collection<Node> createBucket() {
        // LinkedList, ArrayList, HashSet, Stack, and ArrayDeque都可以
        return new LinkedList<>();
    }

    // TODO: Implement the methods of the Map61B Interface below
    // Your code won't compile until you do so!

    @Override
    public void put(K key, V value) {
        int index = Math.floorMod(key.hashCode(), M);
        if (buckets[index] == null) {
            buckets[index] = createBucket();
        }

        for (Node node : buckets[index]) {
            if (node.key.equals(key)) {
                node.value = value;
                return;
            }
        }

        Node e = new Node(key, value);
        buckets[index].add(e);
        N ++;

        if ((double) N / M > Factor) {
            resize(M * 2);
        }
    }

    private void resize(int capacity) {
        Collection<Node>[] returnBuckets = new Collection[capacity];
        int index;
        for (int i = 0; i < M; i++) {
            if (buckets[i] != null) {
                for (Node node : buckets[i]) {
                    index = Math.floorMod(node.key.hashCode(), capacity);

                    if (returnBuckets[index] == null) {
                        returnBuckets[index] = createBucket();
                    }

                    returnBuckets[index].add(node);

                }
            }
        }
        buckets = returnBuckets;
        M = capacity;
    }

    @Override
    public V get(K key) {
        return null;
    }

    @Override
    public boolean containsKey(K key) {
        return false;
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
