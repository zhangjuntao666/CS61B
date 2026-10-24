package core;

import tileengine.TETile;
import tileengine.Tileset;
import utils.RandomUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class World {

    // build your own world!
    public static final int WIDTH = 60;
    public static final int HEIGHT = 40;

    public TETile[][] world;
    private long seed;
    private static Random rand;
    private static List<Square> squareList = new ArrayList<>();
    /*constructor*/
    /*receive the seed from Main to generate the world!*/
    public World (long seed) {
        this.seed = seed;
        this.world = new TETile[WIDTH][HEIGHT];
        this.rand = new Random(seed);
        withNothing();
        generateWorld(); //just for test
    }

    /*initialize the world with nothing*/
    private void withNothing() {
        for (int i = 0; i < WIDTH; i ++) {
            for (int j=0; j < HEIGHT; j ++) {
                world[i][j] = Tileset.NOTHING;
            }
        }
    }

    /*generate a dark world based on the seed with FLOOR_5 and WALL (avatar, light_off, potion, spike, coin)*/
    private void generateWorld() {
        for (int i =0; i < 100; i++) {
            createSquares();
        }
    }


    private void createSquares() {
        int width = RandomUtils.uniform(rand, 4, 9);
        int height = RandomUtils.uniform(rand, 4,9);
        int startX = RandomUtils.uniform(rand, 1, WIDTH - 11);
        int startY = RandomUtils.uniform(rand, 1, HEIGHT - 11);
        Square s = new Square(width, height, startX, startY);
        if (squareList.isEmpty()) {
            squareList.add(s);
            drawSquare(startX, startY, width, height);
            return ;
        } else {
            boolean judge = false;
            for (Square s2 : squareList) {
                judge = s.overlap(s2);
                if (judge) {
                    break;
                }
            }
            if (!judge) {
                squareList.add(s);
                drawSquare(startX, startY, width, height);
                return ;
            }
        }
    }

    private void drawSquare(int startX, int startY, int width, int height) {
        /*floor*/
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                world[startX + i][startY + j] = Tileset.FLOOR_3;
            }
        }
        /*wall*/
        for (int i = 0; i < width + 2; i ++) {
            world[startX + i - 1][startY - 1] = Tileset.WALL;
            world[startX + i - 1][startY + height] = Tileset.WALL;
        }
        for (int j = 0; j < height + 2; j ++) {
            world[startX - 1][startY + j - 1] = Tileset.WALL;
            world[startX + width][startY + j -1] = Tileset.WALL;
        }
    }

    /*因为重叠的square很难看，所以我需要写一个检测两个room是否重叠的方法。
    这就需要将所有现有的square的startX, startY, width, height都用square对象存起来*/
    private static class Square {
        int width;
        int height;
        int startX;
        int startY;

        public Square(int width, int height, int startX, int startY) {
            this.width = width;
            this.height = height;
            this.startX = startX;
            this.startY = startY;
        }

        /*判断两个square是否有重合*/
        private boolean overlap(Square s2) {
            if ((s2.startX - 2 > startX + width) || (s2.startX + s2.width < startX - 2) || (s2.startY - 2 > startY + height) || (s2.startY + s2.height < startY - 2)) {
                return false;
            }
            return true;
        }

        private int getCenterX() {
            return startX + width / 2;
        }
        private int getCenterY() {
            return startY + height / 2;
        }
    }

}
