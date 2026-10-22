package Lab9;

import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;

/**
 * Draws a world initially full of trees.
 */
public class Task1 {
    /**
     * Fills the entire 2D world with the Tileset.TREE tile.
     */
    private static final TETile[][] world = new TETile[30][20];
    private static void fillWithTrees() {
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 20; j++) {
                world[i][j] = Tileset.NOTHING;
            }
        }

        for (int i = 0; i < 30; i++) { //水平方向，即向右时行数i增加。这里和平面直角坐标系是一致的，(i,j)就是坐标
            for (int j = 0; j < 15; j++) {
                world[i][j] = Tileset.TREE;
            }
        }

    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(30, 20);

        fillWithTrees();

        ter.renderFrame(world);
    }
}