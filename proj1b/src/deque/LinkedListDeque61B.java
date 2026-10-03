package deque;

import java.util.ArrayList; // import the ArrayList class
import java.util.List;

public class LinkedListDeque61B<T> implements Deque61B<T> {
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

    /*在不写辅助函数updateNewp的限制下似乎无法完成？！真的没想到一个在不破坏sentinel的情况下将p传递下去的方式！*/
    int i = 0; //这是一个非常不优雅的方式，不过我目前没想到更优雅的方式
    @Override
    public T getRecursive(int index) {
        if (updateNewp(i) == sentinel) {
            return null;
        } else if (index == 0) {
            return updateNewp(i).next.item;
        } else {
            i ++;
            return getRecursive(index - 1);
        }
    }

    /*getDecursive的辅助函数*/
    private DLList updateNewp(int i) {
        int k = 0;
        DLList p = sentinel;
        while (k < i) {
            p = p.next;
            k++;
        }
        return p;
    }
}

