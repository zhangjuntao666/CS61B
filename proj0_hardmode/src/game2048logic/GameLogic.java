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

        } else if (side == Side.EAST) {

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

        }
    }
}
