package Lab9;

import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;

/**
 * Draws a world initially full of trees.
 */
public class Task2 {
    /**
     * Fills the entire 2D world with the Tileset.TREE tile.
     */
    private static final int WIDTH = 30;
    private static final int HEIGHT = 20;
    private static final TETile[][] world = new TETile[WIDTH][HEIGHT];
    private static void fillWithTrees() {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                world[i][j] = Tileset.NOTHING;
            }
        }

        for (int i = 0; i < WIDTH; i++) { //水平方向，即向右时行数i增加。这里和平面直角坐标系是一致的，(i,j)就是坐标
            for (int j = 0; j < HEIGHT - 5; j++) {
                world[i][j] = Tileset.TREE;
            }
        }
    }

    private static void drawSquare(TETile[][] world, int startX, int startY, int size, TETile tile) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                world[startX + j][startY - i] = tile;
            }
        }
    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        fillWithTrees();
        drawSquare(world, 10, 7, 5, Tileset.FLOWER);
        ter.renderFrame(world);
    }
}