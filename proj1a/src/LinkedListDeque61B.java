import java.util.List;
import java.util.ArrayList; // import the ArrayList class

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
        sentinel.prev.prev.next = sentinel.prev;
        size += 1;
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        DLList p = sentinel;

        while (p.next.item != null) {
            returnList.add(p.next.item);
            p = p.next;
        }
        return returnList;
    }

    @Override
    public boolean isEmpty() {
        if (sentinel.next == sentinel) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        if (sentinel.next == sentinel) {
            return null;
        } else {
            T f = sentinel.next.item;
            sentinel.next = sentinel.next.next;
            sentinel.next.prev = sentinel;
            size --;
            return f;
        }
    }

    @Override
    public T removeLast() {
        if (sentinel.prev == sentinel) {
            return null;
        } else {
            T f = sentinel.prev.item;
            sentinel.prev = sentinel.prev.prev;
            sentinel.prev.next = sentinel;
            size --;
            return f;
        }
    }

    @Override
    public T get(int index) {
        int num = 0;
        DLList p = sentinel;
        while (p.next != sentinel) {
            if (num == index) {
                return p.next.item;
            }
            num ++;
            p = p.next;
        }
        return null;
    }

    @Override
    public T getRecursive(int index) {
        DLList p = sentinel;
        if (p.next == sentinel) {
            return null;
        } else if (index == 0) {
            return p.next.item;
        } else {
            index --;
            p = p.next;
            return getRecursive(index);
        }

    }
}
