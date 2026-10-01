package deque;

import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

public class ArrayDeque61B<T> implements Deque61B<T> {
    private int size;
    private int nextFirst;
    private int nextLast;
    public T[] items;
    /*constructor*/
    public ArrayDeque61B() {
        items = (T[]) new Object[8];
        nextFirst = 3;
        nextLast = 4;
        size = 0;
    }

    @Override
    public void addFirst(T x) {
        items[nextFirst] = x;
        size ++;
        if (nextFirst != 0) {
            nextFirst --;
        } else {
            nextFirst = items.length - 1;
        }
    }

    /*省去之后讨论nextFirst的前一项是谁*/
    private int nextFirstPlus1(int nextFirst, T[] items) {
        int l = items.length;
        int nextFirstPlus1;
        if (nextFirst == l - 1) {
            nextFirstPlus1 = 0;
        } else {
            nextFirstPlus1 = nextFirst + 1;
        }
        return nextFirstPlus1;
    }

    @Override
    public void addLast(T x) {
        items[nextLast] = x;
        size ++;
        if (nextLast != items.length - 1) {
            nextLast ++;
        } else {
            nextLast = 0;
        }
    }

    /*nextLastMinus1和nextFirstPlus1作用相同*/
    private int nextLastMinus1(int nextLast, T[] items) {
        int l = items.length;
        int nextLastMinus1;
        if (nextLast == 0) {
            nextLastMinus1 = l - 1;
        } else {
            nextLastMinus1 = nextLast - 1;
        }
        return nextLastMinus1;
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<T>();
        int i = nextFirstPlus1(nextFirst, items);
        returnList.add(items[i]);
        i = nextFirstPlus1(i, items);
        while (i != nextLast) {
            returnList.add(items[i]);
            i = nextFirstPlus1(i, items);
        }
        return returnList;
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
