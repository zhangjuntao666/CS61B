import java.util.List;

public class LinkedListDeque61B<T> implements Deque61B<T>{
    /*nested DLList<T>*/
    private class DLList {
        public T item;
        public DLList prev;
        public DLList next;
        /*constructor of DDList*/
        public DLList(T i, DLList p, DLList n) {
            item = i;
            prev = p;
            next = n;
        }
    }

    private DLList sentinel;
    private int size;

    /*constructor of LinkedListDeque61B*/
    public LinkedListDeque61B() {
        sentinel = new DLList(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }

    @Override
    public void addFirst(T x) {
        sentinel.next = new DLList(x, sentinel, sentinel.next);
        sentinel.next.next.prev = sentinel.next;
        size += 1;
    }

    @Override
    public void addLast(T x) {
        sentinel.prev = new DLList(x, sentinel.prev, sentinel);
        size += 1;
    }

    @Override
    public List<T> toList() {
        return List.of();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        return null;
    }

    @Override
    public T removeLast() {
        return null;
    }

    @Override
    public T get(int index) {
        return null;
    }

    @Override
    public T getRecursive(int index) {
        return null;
    }
}
