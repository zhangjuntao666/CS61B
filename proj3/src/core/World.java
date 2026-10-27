package core;

import tileengine.TETile;
import tileengine.Tileset;
import utils.RandomUtils;
import java.util.*;

public class World {

    // build your own world!
    public static final int WIDTH = 60;
    public static final int HEIGHT = 40;
    private static final int coinScore = 10;
    private static final int healthScore = 50;
    private static final int spikeStep = 3;
    private static final int potionStep = 5;
    private long seed;
    private Random rand;

    public int health = 3;
    public boolean isWin = false;
    public boolean isOver = false;
    public int step = 0;
    public int coins = 0;
    public boolean lightOn = true;
    public StringBuilder history = new StringBuilder(); //用来存SEED和用户的所有有效按键
    private TETile underAvatar = Tileset.FLOOR_4;

    public TETile[][] world;
    private List<Square> squareList = new ArrayList<>();
    public int avatarX;
    public int avatarY;
    private List<Light> lightList = new ArrayList<>();
    private int[][] distances = new int[WIDTH][HEIGHT]; //用来记录每个地砖到光源的距离
    /*constructor*/
    /*receive the seed from Main to generate the world!*/
    public World (long seed) {
        this.seed = seed;
        this.world = new TETile[WIDTH][HEIGHT];
        this.rand = new Random(seed);

        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                distances[i][j] = 999;
            }
        }

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

    /*generate a world based on the seed with light FLOOR_5 and WALL, avatar, light_on, potion, spike, coin, door*/
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
            if (world[startX + i - 1][startY - 1] == Tileset.NOTHING) {
                world[startX + i - 1][startY - 1] = Tileset.WALL;
            }
            if (world[startX + i - 1][startY + height] == Tileset.NOTHING) {
                world[startX + i - 1][startY + height] = Tileset.WALL;
            }
        }
        for (int j = 0; j < height + 2; j ++) {
            if (world[startX - 1][startY + j - 1] == Tileset.NOTHING) {
                world[startX - 1][startY + j - 1] = Tileset.WALL;
            }
            if (world[startX + width][startY + j -1] == Tileset.NOTHING) {
                world[startX + width][startY + j -1] = Tileset.WALL;
            }
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
        avatarX = squareList.get(0).getCenterX();
        avatarY = squareList.get(0).getCenterY();
        world[avatarX][avatarY] = Tileset.AVATAR;
        lightList.add(new Light(avatarX, avatarY));
        distances[avatarX][avatarY] = 0;

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
                    //方便后续距离计算
                    lightList.add(new Light(lx, ly));
                    distances[lx][ly] = 0;
                }
            }
        }
    }

    /*创建一个Light类来存所有光源的坐标*/
    private class Light {
        int x, y;
        public Light(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    /*生成明亮或者黑暗的世界，用于ter的渲染*/
    public TETile[][] renderWorld() {
        TETile[][] renderLightWorld = TETile.copyOf(world);
        TETile[][] renderDarkWorld = TETile.copyOf(world);


        if (lightOn) {
            int[][] lightDistance = calculateLightBFS();
            for (int i = 0; i < WIDTH; i++) {
                for (int j = 0; j < HEIGHT; j++) {
                    if ((lightDistance[i][j] == 1) && (world[i][j] == Tileset.FLOOR_4)) {
                        renderLightWorld[i][j] = Tileset.FLOOR_1;
                    }
                    if ((lightDistance[i][j] == 2) && (world[i][j] == Tileset.FLOOR_4)) {
                        renderLightWorld[i][j] = Tileset.FLOOR_2;
                    }
                    if ((lightDistance[i][j] == 3) && (world[i][j] == Tileset.FLOOR_4)) {
                        renderLightWorld[i][j] = Tileset.FLOOR_3;
                    }
                }
            }
            return renderLightWorld;
        }

        /*生成darkWorld*/
        int[][] avatarDistance = calculateAvatarBFS();
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                if ((avatarDistance[i][j] == 1) && (world[i][j] == Tileset.FLOOR_4)) {
                    renderDarkWorld[i][j] = Tileset.FLOOR_1;
                }
                if ((avatarDistance[i][j] == 2) && (world[i][j] == Tileset.FLOOR_4)) {
                    renderDarkWorld[i][j] = Tileset.FLOOR_2;
                }
                if ((avatarDistance[i][j] == 3) && (world[i][j] == Tileset.FLOOR_4)) {
                    renderDarkWorld[i][j] = Tileset.FLOOR_3;
                }
                if (world[i][j] == Tileset.LIGHT_ON) {
                    renderDarkWorld[i][j] = Tileset.LIGHT_OFF;
                }
                if (world[i][j] == Tileset.POTION) {
                    if (avatarDistance[i][j] > 5) {
                        renderDarkWorld[i][j] = Tileset.POTION_DARK;
                    }
                }
                if (world[i][j] == Tileset.SPIKE) {
                    if (avatarDistance[i][j] > 5) {
                        renderDarkWorld[i][j] = Tileset.SPIKE_DARK;
                    }
                }
                if (world[i][j] == Tileset.DOOR) {
                    if (avatarDistance[i][j] > 5) {
                        renderDarkWorld[i][j] = Tileset.DOOR_DARK;
                    }
                }
            }


        }
        return renderDarkWorld;
    }

    /*用于黑暗世界。entity也要变！输出一个对于黑暗世界的distances*/
    private int[][] calculateAvatarBFS() {
        int[][] avatarDistance = new int[WIDTH][HEIGHT];
        Queue<Light> lightQueue = new ArrayDeque<>();
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                avatarDistance[i][j] = 999;
            }
        }
        avatarDistance[avatarX][avatarY] = 0;
        lightQueue.add(new Light(avatarX, avatarY));

        int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dy = {1, 0, -1, 1, -1, 1, 0, -1};

        while (!lightQueue.isEmpty()) {
            Light l1 = lightQueue.poll();
            int x = l1.x;
            int y = l1.y;

            for (int k = 0; k < 8; k++) {
                int x1 = x + dx[k];
                int y1 = y + dy[k];
                if ((x1 >= 0) && (x1 <= WIDTH - 1) && (y1 >= 0) && (y1 <= HEIGHT - 1) && (world[x1][y1] != Tileset.WALL) && (avatarDistance[x1][y1] > avatarDistance[x][y] + 1)) {
                    avatarDistance[x1][y1] = avatarDistance[x][y] + 1;
                    lightQueue.offer(new Light(x1, y1));
                }
            }
        }

        return avatarDistance;
    }

    /*是需要根据distances对tile进行操作就行。输出的是一个新的，用于明亮世界的distances*/
    private int[][] calculateLightBFS() {
        int[][] lightDistance = new int[WIDTH][HEIGHT];
        Queue<Light> lightQueue = new ArrayDeque<>();
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                lightDistance[i][j] = 999;
            }
        }

        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                if (world[i][j] == Tileset.LIGHT_ON) {
                    lightDistance[i][j] = distances[i][j];
                    lightQueue.add(new Light(i, j));
                }
            }
        }

        lightDistance[avatarX][avatarY] = 0;
        lightQueue.add(new Light(avatarX, avatarY));

        int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dy = {1, 0, -1, 1, -1, 1, 0, -1};

        while (!lightQueue.isEmpty()) {
            Light l1 = lightQueue.poll();
            int x = l1.x;
            int y = l1.y;

            for (int k = 0; k < 8; k++) {
                int x1 = x + dx[k];
                int y1 = y + dy[k];
                if ((x1 >= 0) && (x1 <= WIDTH - 1) && (y1 >= 0) && (y1 <= HEIGHT - 1) && (world[x1][y1] != Tileset.WALL) && (lightDistance[x1][y1] > lightDistance[x][y] + 1)) {
                    lightDistance[x1][y1] = lightDistance[x][y] + 1;
                    lightQueue.offer(new Light(x1, y1));
                }
            }
        }

        return lightDistance;
    }

    public void moveAvatar(int dx, int dy) {
        if (isOver || isWin) {
            return ;
        }

        int newX = avatarX + dx;
        int newY = avatarY + dy;

        if (newX >= 0 && newX < WIDTH && newY >= 0 && newY < HEIGHT) {
            TETile target = world[newX][newY];

            if (target == Tileset.WALL || target == Tileset.WALL_DARK || target == Tileset.NOTHING) {
                return ;
            }

            if (target == Tileset.COIN) {
                coins ++;
            } else if (target == Tileset.POTION || target == Tileset.POTION_DARK) {
                if (health < 3) {
                    health ++;
                }
            } else if (target == Tileset.SPIKE || target == Tileset.SPIKE_DARK) {
                health --;
                if (health < 0) {
                    isOver = true;
                }
            } else if (target == Tileset.DOOR || target == Tileset.DOOR_DARK) {
                isWin = true;
            }

            if (underAvatar == Tileset.LIGHT_OFF || underAvatar == Tileset.LIGHT_ON) {
                world[avatarX][avatarY] = underAvatar;
            } else {
                world[avatarX][avatarY] = Tileset.FLOOR_4;
            }
            avatarX = newX;
            avatarY = newY;
            underAvatar = target;
            world[avatarX][avatarY] = Tileset.AVATAR;
        }

        step ++;
        if (lightOn) {
            createSpike();
        } else {
            if (step % spikeStep == 0) {
                createSpike();
            }
        }

        if (step % potionStep == 0) {
            createPotion();
        }
    }

    private void createSpike() {
        int rx, ry;
        for (int i = 0; i < 50; i++) {
            rx = RandomUtils.uniform(rand, 0, WIDTH);
            ry = RandomUtils.uniform(rand, 0, HEIGHT);
            if (world[rx][ry] == Tileset.FLOOR_4 && (rx != avatarX || ry != avatarY)) {
                world[rx][ry] = Tileset.SPIKE;
                return ;
            }
        }
    }

    private void createPotion() {
        int rx, ry;
        for (int i = 0; i < 50; i++) {
            rx = RandomUtils.uniform(rand, 0, WIDTH);
            ry = RandomUtils.uniform(rand, 0, HEIGHT);
            if (world[rx][ry] == Tileset.FLOOR_4 && (rx != avatarX || ry != avatarY)) {
                world[rx][ry] = Tileset.POTION;
                return ;
            }
        }
    }

    public int score() {
        int score = 0;
        if (isWin) {
            score += coins * coinScore;
            score += health * healthScore;
        }
        if (isOver) {
            score = 0;
        }
        return score;
    }

    public String getDescription(int x, int y) {
        if (x < 0 || x >= WIDTH || y < 0 || y >= HEIGHT) {
            return "gulagula~";
        }
        return world[x][y].description();
    }
}
