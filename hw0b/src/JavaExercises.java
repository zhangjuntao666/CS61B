import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class JavaExercises {

    /** Returns an array [1, 2, 3, 4, 5, 6] */
    public static int[] makeDice() { //int[] 是 array 对象
        // TODO: Fill in this function.
        int[] arr = new int[6];
        for (int i=0; i<=5; i++) {
            arr[i] = i + 1;
        }
        return arr;
    }

    /** Returns the order depending on the customer.
     *  If the customer is Ergun, return ["beyti", "pizza", "hamburger", "tea"].
     *  If the customer is Erik, return ["sushi", "pasta", "avocado", "coffee"].
     *  In any other case, return an empty String[] of size 3. */
    public static String[] takeOrder(String customer) {
        // TODO: Fill in this function.
        String[] ergunOrder = new String[] {"beyti", "pizza", "hamburger", "tea"};
        String[] erikOrder = new String[] {"sushi", "pasta", "avocado", "coffee"};
        if (Objects.equals(customer, "Ergun")) {
            return ergunOrder;
        } else if (Objects.equals(customer, "Erik")) {
            return erikOrder;
        }
        return new String[3];
    }

    /** Returns the positive difference between the maximum element and minimum element of the given array.
     *  Assumes array is nonempty. */
    public static int findMinMax(int[] array) {
        // TODO: Fill in this function.
        int max = array[0];
        int min = array[0];
        for (int i=0; i<array.length; i++){
            if (array[i] >= max) {
                max = array[i];
                continue;
            }
            min = array[i];
        }
        return max-min;
    }

    /**
      * Uses recursion to compute the hailstone sequence as a list of integers starting from an input number n.
      * Hailstone sequence is described as:
      *    - Pick a positive integer n as the start
      *        - If n is even, divide n by 2
      *        - If n is odd, multiply n by 3 and add 1
      *    - Continue this process until n is 1
      */
    public static List<Integer> hailstone(int n) {
        return hailstoneHelper(n, new ArrayList<>());
    }

    private static List<Integer> hailstoneHelper(int x, List<Integer> list) {
        // TODO: Fill in this function.
        list.add(x);
        while (x != 1) {
            if (x % 2 == 0) {
                x = x / 2;
            } else {
                x = 3 * x + 1;
            }
            list.add(x);
        }
        return list;
    }

}
