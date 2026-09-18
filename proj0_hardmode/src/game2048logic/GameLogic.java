package game2048logic;

import game2048rendering.Side;
import static game2048logic.MatrixUtils.rotateLeft;
import static game2048logic.MatrixUtils.rotateRight;

/**
 * @author  Josh Hug
 */
public class GameLogic {
    /**
     * Modifies the board to simulate tilting the entire board to
     * the given side.
     *
     * @param board the current state of the board
     * @param side  the direction to tilt
     */
    public static void tilt(int[][] board, Side side) {
        // fill this in
        int sizeOfBoard = board[0].length; //获取board的行数或列数

        if (side == Side.NORTH) {
            // Don't you dare try to write all of your
            // code in this method. You will want to write
            // helper methods. And those helper methods should
            // have helper methods.

            return;
        } else if (side == Side.EAST) {
            return;
        } else if (side == Side.WEST) {
            return;
        } else { // SOUTH
            return;
        }
    }

    /*merge的作用是从side端开始，沿着反方向遍历每一个元素，判断他和后面一个元素是否相等。如果相等，则将这个元素的值乘以2，后面的元素赋值为0。这样做不需要考虑会改变原先0的位置*/
    public static int[][] merge(int[][] board, Side side) {
        int sizeOfBoard = board[0].length; //获取board的行数或列数
        if (side == Side.NORTH) {
            for (int j=0; j<sizeOfBoard; j++) {
                for (int i=0; i<sizeOfBoard-1; i++) {
                    if (board[i][j] == board[i+1][j]) {
                        board[i][j] *= 2;
                        board[i+1][j] = 0;
                    }
                }
            }
        } else if (side == Side.EAST) {
            for (int i=0; i<sizeOfBoard; i++) {
                for (int j=sizeOfBoard-1; j>=1; j--) {
                    if (board[i][j] == board[i][j-1]) {
                        board[i][j] *=2;
                        board[i][j-1] = 0;
                    }
                }
            }
        } else if (side == Side.WEST) {
            for (int i=0; i<sizeOfBoard; i++) {
                for (int j=0; j<sizeOfBoard-1; j++) {
                    if (board[i][j] == board[i][j+1]) {
                        board[i][j] *= 2;
                        board[i][j+1] = 0;
                    }
                }
            }
        } else {
            for (int j=0; j<sizeOfBoard; j++) {
                for (int i=sizeOfBoard-1; i>=1; i--) {
                    if (board[i][j] == board[i-1][j]) {
                        board[i][j] *= 2;
                        board[i-1][j] = 0;
                    }
                }
            }
        }
        return board;
    }

    /*judge0的作用是判断某一个int[]是否全为0，全0返回true，不需要再进行move的操作。如果返回值是false，则需要进一步执行count0或count0rev，*/
    public static boolean judge0(int[] list) {
        for (int i=0; i<list.length; i++) {
            if (list[i] != 0) {
                return false;
            }
        }
        return true;
    }

    /*moveForward的作用是把一个非零的数组中的所有非零项往前面移，指导前面全是非零项，后面全是0。用于NORTH和WEST*/
    public static int[] moveForward(int[] list) {//目前的想法是从左到右依次进行，对于每一个
        int currentIndex = 0;
        for (int i=0; i<list.length; i++) {
            if (list[i] != 0 && i != currentIndex) {
                list[currentIndex] = list[i];
                list[i] = 0;
                currentIndex ++;
            } else if (list[i] != 0 && i == currentIndex) {
                currentIndex ++;
            }
        }
        return list;
    }

    /*moveBackward的作用和moveForward差不多，不过是把数组的非零元素往后移。用于SOUTH和EAST*/
    public static int[] moveBackward(int[] list) {
        int currentIndex = list.length-1;
        for (int i=list.length-1; i>=0; i--) {
            if (list[i] != 0 && i != currentIndex) {
                list[currentIndex] = list[i];
                list[i] = 0;
                currentIndex --;
            } else if (list[i] != 0 && i == currentIndex) {
                currentIndex --;
            }
        }
        return list;
    }
}
