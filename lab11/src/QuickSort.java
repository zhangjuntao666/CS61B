public class QuickSort {

    /**
     * @param arr
     *
     * Sort the array arr using quicksort with the 3-scan partition algorithm.
     * The quicksort algorithm is as follows:
     * 1. Select a pivot, partition array in place around the pivot.
     * 2. Recursively call quicksort on each subsection of the modified array.
     */
    public static int[] sort(int[] arr) {
        quickSort(arr, 0, arr.length - 1);
        return arr;
    }

    /**
     * @param arr
     * @param start
     * @param end
     *
     * Helper method for sort: runs quicksort algorithm on array from [start:end)
     */
    private static void quickSort(int[] arr, int start, int end) {
        int len = arr.length;
        if (start >= end) {
            return;
        } else {
            int[] indexArr = partition(arr, start, end);
            int less = indexArr[0];
            int greater = indexArr[1];
            quickSort(arr, start, less);
            quickSort(arr, greater, end);
        }
    }

    /**
     * @param arr
     * @param start
     * @param end
     *
     * Partition the array in-place following the 3-scan partitioning scheme.
     * You may assume that first item is always selected as the pivot.
     * 
     * Returns a length-2 int array of indices:
     * [end index of "less than" section, start index of "greater than" section]
     *
     * Most of the code for quicksort is in this function
     */
    private static int[] partition(int[] arr, int start, int end) {
        int[] indexArr = new int[2];
        //先用≤和>指针找出start
        int i = start;
        int j = end;
        int pivot = arr[start];
        while (i <= j && i <= end) {
            if (arr[i] <= pivot) {
                i++;
                continue;
            } else if (arr[j] > pivot) {
                j--;
                continue;
            } else if (arr[i] > pivot && arr[j] <= pivot) {
                int value = arr[i];
                arr[i] = arr[j];
                arr[j] = value;
                i++;
                j--;
                continue;
            }
        }
        indexArr[1] = i;
        //再用<和==指针找出end
        i = start;
        j = indexArr[1] - 1;
        while (i <= j && j >= start) {
            if (arr[i] < pivot) {
                i++;
                continue;
            } else if (arr[j] == pivot) {
                j--;
                continue;
            } else if (arr[i] == pivot && arr[j] < pivot) {
                int value = arr[i];
                arr[i] = arr[j];
                arr[j] = value;
                i++;
                j--;
                continue;
            }
        }
        indexArr[0] = j;
        return indexArr;
    }
}   
