package Lab9;

import edu.princeton.cs.algs4.StdDraw;
import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;
import utils.RandomUtils;

import java.util.Random;

/**
 * Draws a world initially full of trees.
 */
public class Task3 {
    /**
     * Fills the entire 2D world with the Tileset.TREE tile.
     */
    private static final int WIDTH = 30;
    private static final int HEIGHT = 20;

    private static final long SEED = 2873123;
    private static final Random rand = new Random(SEED);

    private static TETile[][] world = new TETile[WIDTH][HEIGHT];

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

    private static TETile randomTile() {
        int tileNum = rand.nextInt(3);
        return switch (tileNum) {
            case 0 -> Tileset.FLOWER;
            case 1 -> Tileset.WALL;
            case 2 -> Tileset.WATER;
            default -> Tileset.NOTHING;
        };
    }

    private static void addRandomSquare(TETile[][] world, Random rand) {
        int size = RandomUtils.uniform(rand, 3, 8);
        int startX = RandomUtils.uniform(rand, 0, WIDTH - size);
        int startY = RandomUtils.uniform(rand, size, HEIGHT - 5);

        drawSquare(world, startX, startY, size, randomTile());
    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        fillWithTrees();

        char c;
        while (true) {
            while (StdDraw.hasNextKeyTyped()) {
                c = StdDraw.nextKeyTyped();
                c = Character.toLowerCase(c);

                switch (c) {
                    case 'n':
                        addRandomSquare(world, rand);
                        break;
                    case 'q':
                        System.exit(0);
                        break;
                    default:
                        break;
                }
            }

            ter.renderFrame(world);
        }
    }
}