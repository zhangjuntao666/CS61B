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

    /*resize up for add*/
    private void resizeUp() {
        T[] r = (T[]) new Object[size * 2];
        int i = nextFirstPlus1(nextFirst, items);
        int j = 0;
        r[j] = items[i];
        i = nextFirstPlus1(i, items);
        j ++;
        while (i != nextLast) {
            r[j] = items[i];
            i = nextFirstPlus1(i, items);
            j ++;
        }
        nextFirst = r.length - 1;
        nextLast = size;
        items = r;
    }

    @Override
    public void addFirst(T x) {
        if (size == items.length) {
            resizeUp();
        }
        items[nextFirst] = x;
        size ++;
        nextFirst = nextLastMinus1(nextFirst, items);
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
        if (size == items.length) {
            resizeUp();
        }
        items[nextLast] = x;
        size ++;
        nextLast = nextFirstPlus1(nextLast, items);
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
        if (size == 0) {
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
        float usage =  (float) size / items.length;
        if (items.length >= 16 && usage <= 0.25) {
            resizeUp();
        }
        if (size != 0) {
            nextFirst = nextFirstPlus1(nextFirst, items);
            T fail = items[nextFirst];
            items[nextFirst] = null;
            size --;
            return fail;
        } else {
            return null;
        }
    }

    @Override
    public T removeLast() {
        if (size != 0) {
            nextLast = nextLastMinus1(nextLast, items);
            T fail = items[nextLast];
            items[nextLast] = null;
            size --;
            return fail;
        } else {
            return null;
        }
    }

    @Override
    public T get(int index) {
        if (index >= 0 && index < items.length) {
            int First = nextFirstPlus1(nextFirst, items);
            if (First + index < items.length) {
                return items[First + index];
            } else {
                return items[First + index - items.length];
            }
        }
        return null;
    }

    @Override
    public T getRecursive(int index) {
        throw new UnsupportedOperationException("No need to implement getRecursive for proj 1b");
    }
}
