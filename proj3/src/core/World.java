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
    private Random rand;
    private List<Square> squareList = new ArrayList<>();
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

    /*generate a totally light world based on the seed with FLOOR_5 and WALL, avatar, light_on, potion, spike, coin, door*/
    private void generateWorld() {
        for (int i =0; i < 100; i++) {
            createSquares();
            createHallway();
        }
        createEntity(); //产生其他entities(明亮状态)
    }

    /*画出没有走廊的world*/
    private void createSquares() {
        int width = RandomUtils.uniform(rand, 4, 9);
        int height = RandomUtils.uniform(rand, 4,9);
        int startX = RandomUtils.uniform(rand, 1, WIDTH - 9);
        int startY = RandomUtils.uniform(rand, 1, HEIGHT - 9);
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

    /*画水平的走廊*/
    private void drawHorizontal(int x1, int x2, int y) {
        int startX = Math.min(x1, x2);
        int endX = Math.max(x1, x2);
        for (int x = startX; x <= endX; x++) {
            world[x][y] = Tileset.FLOOR_4;
            /*在走廊两侧加墙*/
            if (world[x][y + 1] == Tileset.NOTHING) {
                world[x][y + 1] = Tileset.WALL;
            }
            if (world[x][y - 1] == Tileset.NOTHING) {
                world[x][y - 1] = Tileset.WALL;
            }
        }
    }

    /*画竖直的走廊*/
    private void drawVertical(int y1, int y2, int x) {
        int startY = Math.min(y1, y2);
        int endY = Math.max(y1, y2);
        for (int y = startY; y <= endY; y++) {
            world[x][y] = Tileset.FLOOR_4;
            // 走廊左右加墙
            if (world[x + 1][y] == Tileset.NOTHING) {
                world[x + 1][y] = Tileset.WALL;
            }
            if (world[x - 1][y] == Tileset.NOTHING) {
                world[x - 1][y] = Tileset.WALL;
            }
        }
    }

    /*画两个square之间的走廊*/
    private void createHallway() {
        if (squareList.size() >= 2) {
            Square s1 = squareList.get(squareList.size() - 1);
            Square s2 = squareList.get(squareList.size() - 2);
            drawHorizontal(s1.getCenterX(), s2.getCenterX(), s1.getCenterY());
            drawVertical(s1.getCenterY(), s2.getCenterY(), s2.getCenterX());
        }
    }

    private void drawSquare(int startX, int startY, int width, int height) {
        /*floor*/
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                world[startX + i][startY + j] = Tileset.FLOOR_4;
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

    /*创建avatar, light_on, potion, spike, coin, door*/
    private void createEntity() {
        /*将avatar放在第一个room的中心*/
        int playerX = squareList.get(0).getCenterX();
        int playerY = squareList.get(0).getCenterY();
        world[playerX][playerY] = Tileset.AVATAR;

        //lightUp(playerX, playerY); //lightWorld时就不需要了

        /*将出口door放在最后一个room的中心*/
        int size = squareList.size();
        int doorX = squareList.get(size - 1).getCenterX();
        int doorY = squareList.get(size - 1).getCenterY();
        world[doorX][doorY] = Tileset.DOOR;

        randomLightEntity(Tileset.COIN, 20);
        randomLightEntity(Tileset.SPIKE, 15);
        randomLightEntity(Tileset.POTION, 9);

        putLight();
    }

    /*再房间的随机tile上放指定数量的指定tile（亮版）,用于生成最开始明亮的world中的coin, potion和spike*/
    private void randomLightEntity(TETile tile, int num) {
        int sum = 0;
        while (sum < num) {
            int randX = RandomUtils.uniform(rand, 0, WIDTH - 1);
            int randY = RandomUtils.uniform(rand, 0, HEIGHT - 1);
            if (world[randX][randY] == Tileset.FLOOR_4) {
                world[randX][randY] = tile;
                sum ++;
            }
        }
    }

    /*在每个房间里都放一盏灯，位置随机。它周围一圈的FLOOR_4改用FLOOR_1，其次是FLOOR_2和FLOOR_3做多度。*/
    private void putLight() {
        for (Square s : squareList) {
            boolean setYet = false;
            int lx = 0;
            int ly = 0;
            while (setYet == false) {
                lx = RandomUtils.uniform(rand, s.startX, s.startX + s.width - 1);
                ly = RandomUtils.uniform(rand, s.startY, s.startY + s.height - 1);
                if (world[lx][ly] == Tileset.FLOOR_4) {
                    world[lx][ly] = Tileset.LIGHT_ON;
                    setYet = true;
                }
            }
            lightUp(lx, ly); //让每个灯旁边一圈亮起来！
        }
    }

    /*将avatar和light周围的FLOOR_4点亮*/
    private void lightUp(int x, int y) {
        /*FLOOR_1*/
        for (int i = 0; i < 2; i ++) {
            if (world[x - 1][y - 1 + i] == Tileset.FLOOR_4) {
                world[x - 1][y - 1 + i] = Tileset.FLOOR_1;
            }
            if (world[x + 1][y + i] == Tileset.FLOOR_4) {
                world[x + 1][y + i] = Tileset.FLOOR_1;
            }
            if (world[x - 1 + i][y + 1] == Tileset.FLOOR_4) {
                world[x - 1 + i][y + 1] = Tileset.FLOOR_1;
            }
            if (world[x + i][y - 1] == Tileset.FLOOR_4) {
                world[x + i][y - 1] = Tileset.FLOOR_1;
            }
        }

        /*FLOOR_2*/
        for (int i = 0; i < 4; i ++) {
            if (world[x - 2][y - 1 + i] == Tileset.FLOOR_4) {
                world[x - 2][y - 1 + i] = Tileset.FLOOR_2;
            }
            if (world[x + 2][y - 2 + i] == Tileset.FLOOR_4) {
                world[x + 2][y - 2 + i] = Tileset.FLOOR_2;
            }
            if (world[x - 2 + i][y - 2] == Tileset.FLOOR_4) {
                world[x - 2 + i][y - 2] = Tileset.FLOOR_2;
            }
            if (world[x - 1 + i][y + 2] == Tileset.FLOOR_4) {
                world[x - 1 + i][y + 2] = Tileset.FLOOR_2;
            }
        }

        /*FLOOR_3*/
        for (int i = 0; i < 6; i++) {
            if (world[x - 3][y - 2 + i] == Tileset.FLOOR_4) {
                world[x - 3][y - 2 + i] = Tileset.FLOOR_3;
            }
            if (world[x + 3][y - 3 + i] == Tileset.FLOOR_4) {
                world[x + 3][y - 3 + i] = Tileset.FLOOR_3;
            }
            if (world[x - 3 + i][y - 3] == Tileset.FLOOR_4) {
                world[x - 3 + i][y - 3] = Tileset.FLOOR_3;
            }
            if (world[x - 2 + i][y + 3] == Tileset.FLOOR_4) {
                world[x - 2 + i][y + 3] = Tileset.FLOOR_3;
            }
        }
    }

    /*生成明亮（默认）或者黑暗的世界，用于ter的渲染*/
    /*
    public TETile[][] renderWorld(boolean lightOn, int playerX, int playerY) {
        TETile[][] renderWorld = TETile.copyOf(world);

        if (lightOn) {
            return  renderWorld;
        }

        int[][] avatarDistance = calculateAvatarBFS(playerX, playerY);

    }

    private int[][] calculateAvatarBFS(int px, int py) {

    }*/
}
