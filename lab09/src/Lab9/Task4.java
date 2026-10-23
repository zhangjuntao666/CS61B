package Lab9;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;
import utils.RandomUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

/**
 * Draws a world initially full of trees.
 */
public class Task4 {
    /**
     * Fills the entire 2D world with the Tileset.TREE tile.
     */
    private static final int WIDTH = 30;
    private static final int HEIGHT = 20;

    private static final long SEED = 2873123;
    private static  Random rand = new Random(SEED);

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

    private static TETile randomTile(Random rand) {
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

        drawSquare(world, startX, startY, size, randomTile(rand));
    }

    /*save*/
    private static void saveN(int sum) throws FileNotFoundException {
        File file = new File("saveN");
        PrintWriter out = new PrintWriter(file); //这里是直接覆盖原有的值
        out.println(sum);
        out.close();
    }

    /*load*/
    private static int loadN() {
        String fileName = "saveN";
        File file = new File(fileName);
        In in = new In(file);
        if (!in.exists()) {
            return 0;
        }
        int sum = in.readInt();
        in.close();
        return sum;
    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        fillWithTrees();
        int sum = 0;
        char c;
        while (true) {
            while (StdDraw.hasNextKeyTyped()) {
                c = StdDraw.nextKeyTyped();
                c = Character.toLowerCase(c);

                switch (c) {
                    case 'n':
                        addRandomSquare(world, rand);
                        sum ++;
                        break;
                    case 'q':
                        System.exit(0);
                        break;
                    case 's':
                        try {
                            saveN(sum);
                        } catch (Exception e) {}
                        break;
                    case 'l':
                        int n = loadN();
                        Random rand = new Random(SEED);
                        fillWithTrees();
                        for (int i = 0; i < n; i++) {
                            addRandomSquare(world, rand);
                        }
                        ter.renderFrame(world);
                        StdDraw.setPenColor(StdDraw.WHITE);
                        StdDraw.textLeft(1, 17, "Number of squares: " + n);
                        StdDraw.show();
                        StdDraw.pause(2);
                        sum = n;
                        break;
                    default:
                        break;
                }
            }
            ter.renderFrame(world);
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.textLeft(1, 17, "Number of squares: " + sum);
            StdDraw.show();
            StdDraw.pause(2);
        }
    }
}