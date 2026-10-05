package deque;
import java.util.Comparator;
import java.util.Iterator;

public class Maximizer61B {
    /**
     * Returns the maximum element from the given iterable of comparables.
     * You may assume that the iterable contains no nulls.
     *
     * @param iterable  the Iterable of T
     * @return          the maximum element
     */
    public static <T extends Comparable<T>> T max(Iterable<T> iterable) {
        // wizard是通过调用Iterable的一个实例iterable的iterator()方法生产出来的可以遍历集合的一个Iterator
        // wizard很神奇，作为Iterator的它有hasNext和next两个方法
        // 其中next非常神奇，它可以放回wizard的index对应于iterable(一个可遍历的集合)的值，并且index += 1;
        Iterator<T> wizard = iterable.iterator();
        if (!wizard.hasNext()) {
            return null;
        }

        T maximum = wizard.next();
        while (wizard.hasNext()) {
            T current = wizard.next();
            if (maximum.compareTo(current) < 0) {
                maximum = current;
            }
        }
        return maximum;
    }

    /**
     * Returns the maximum element from the given iterable according to the specified comparator.
     * You may assume that the iterable contains no nulls.
     *
     * @param iterable  the Iterable of T
     * @param comp      the Comparator to compare elements
     * @return          the maximum element according to the comparator
     */
    public static <T> T max(Iterable<T> iterable, Comparator<T> comp) {
        Iterator<T> wizard = iterable.iterator();
        if(!wizard.hasNext()) {
            return null;
        }

        T maximum = wizard.next();
        while (wizard.hasNext()) {
            T current = wizard.next();
            if (comp.compare(maximum, current) < 0) {
                maximum = current;
            }
        }
        return maximum;
    }

    public static void main(String[] args) {
        // The style checker will complain about this main method, feel free to delete.

        ArrayDeque61B<Integer> ad = new ArrayDeque61B<>();
        ad.addLast(5);
        ad.addLast(12);
        ad.addLast(17);
        ad.addLast(23);
        System.out.println(max(ad));
    }
}
