public class MergeSort {


    /**
     * @param arr
     *
     * Sort the array arr using merge sort.
     * The merge sort algorithm is as follows:
     * 1. Split the collection to be sorted in half.
     * 2. Recursively call merge sort on each half.
     * 3. Merge the sorted half-lists.
     *
     */
    public static int[] sort(int[] arr) {
        // 先把arr分成一个个独立的元素，再将他们重新merge起来
        int len = arr.length;
        int[] arr1 = new int[len / 2];
        int[] arr2 = new int[len - len / 2];
        if (len <= 1) {
            return arr;
        }
        for (int i = 0; i < len / 2; i++) {
            arr1[i] = arr[i];
        }
        for (int j = len / 2; j < len; j++) {
            arr2[j - len / 2] = arr[j];
        }
        return merge(sort(arr1), sort(arr2));
    }

    /**
     * @param a
     * @param b
     *
     * Merge the sorted half-lists.
     *
     * Suggested helper method that will make it easier for you to implement merge sort.
     */
    private static int[] merge(int[] a, int[] b) {
        int[] c = new int[a.length + b.length];
        int i = 0; //for a
        int j = 0; //for b
        int k = 0; //for c
        while (i < a.length && j < b.length) {
            if (a[i] <= b[j]) {
                c[k] = a[i];
                i++;
            } else {
                c[k] = b[j];
                j++;
            }
            k++;
        }

        while (i < a.length) {
            c[k] = a[i];
            i++;
            k++;
        }

        while (j < b.length) {
            c[k] = b[j];
            j++;
            k++;
        }
        return c;
    }
}

