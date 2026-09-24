import java.util.List;

public class LinkedListDeque61B<T> implements Deque61B<T>{
    /*nested DLList<T>*/
    private static class DLList<T> {
        public T item;
        public DLList<T> prev;
        public DLList<T> next;
        /*constructor of DDList*/
        public DLList(T i, DLList<T> p, DLList<T> n) {
            item = i;
            prev = p;
            next = n;
        }
    }

    private DLList<T> sentinel;
    private int size;

    /*constructors of LinkedListDeque61B*/
    public LinkedListDeque61B(T x) {


    }

    public LinkedListDeque61B() {

    }

    @Override
    public void addFirst(T x) {

    }

    @Override
    public void addLast(T x) {

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
        return 0;
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
