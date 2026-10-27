package core;

import edu.princeton.cs.algs4.StdDraw;
import tileengine.TERenderer;
import tileengine.TETile;

import java.awt.*;
import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // build your own world!
        int hudHeight = 5;
        int menu = 0;
        int seedInput = 1;
        int game = 2;
        int gameOver = 3;
        String seedString = "";
        long seed = 0;
        World world = null;
        int status = menu;

        TERenderer ter = new TERenderer();
        ter.initialize(World.WIDTH, World.HEIGHT + hudHeight);

        while (true) {
            StdDraw.clear(StdDraw.BLACK);

            if (status == menu) {
                StdDraw.setPenColor(StdDraw.WHITE);
                StdDraw.setFont(new Font(Font.MONOSPACED, Font.BOLD, 70));
                StdDraw.text(30, 30, "Welcome to my world!");
                StdDraw.setFont(new Font(Font.MONOSPACED, Font.BOLD, 50));
                StdDraw.text(30, 20, "(N) New Game");
                StdDraw.text(30, 15, "(L) Load Game");
                StdDraw.text(30, 10, "(Q) Quit Game");

                if (StdDraw.hasNextKeyTyped()) {
                    char c = StdDraw.nextKeyTyped();
                    c = Character.toLowerCase(c);

                    switch (c) {
                        case 'n':
                            status = seedInput;
                            break;
                        case 'l':
                            try {
                                File file = new File("save.txt");
                                if (!file.exists()) {
                                    System.out.println("没有找到文件");
                                    break;
                                }
                                Scanner scanner = new Scanner(file);
                                long savedSeed = scanner.nextLong();
                                String savedHistory = scanner.next();
                                scanner.close();

                                world = new World(savedSeed);
                                seed = savedSeed;
                                world.history.append(savedHistory);

                                for (char move : savedHistory.toCharArray()) {
                                    switch (move) {
                                        case 'w':
                                            world.moveAvatar(0, 1);
                                            break;
                                        case 's':
                                            world.moveAvatar(0, -1);
                                            break;
                                        case 'a':
                                            world.moveAvatar(-1, 0);
                                            break;
                                        case 'd':
                                            world.moveAvatar(1, 0);
                                            break;
                                        case 'e':
                                            world.lightOn = !world.lightOn;
                                            break;
                                    }
                                }
                                status = game;
                            } catch (Exception e) {
                                System.out.println("读档失败惹： " + e.getMessage());
                            }
                            break;
                        case 'q':
                            System.exit(0);
                            break;
                        default:
                            break;
                    }
                }
            }

            if (status == seedInput) {
                StdDraw.setPenColor(StdDraw.WHITE);
                StdDraw.setFont(new Font(Font.MONOSPACED, Font.BOLD, 50));
                StdDraw.text(30, 30, "Enter seed followed by S");
                StdDraw.setPenColor(StdDraw.YELLOW);
                StdDraw.text(30, 20, seedString);

                while (StdDraw.hasNextKeyTyped()) {
                    char c = StdDraw.nextKeyTyped();
                    c = Character.toLowerCase(c);

                    if (Character.isDigit(c)) {
                        seedString += c;

                    } else if (c == 's' && !seedString.isEmpty()) {
                        seed = Long.parseLong(seedString);
                        world = new World(seed);
                        status = game;
                    }
                }
            }

            if (status == game) {
                if (StdDraw.hasNextKeyTyped()) {
                    char c = StdDraw.nextKeyTyped();
                    c = Character.toLowerCase(c);

                    switch (c) {
                        case 'w':
                            world.moveAvatar(0, 1);
                            world.history.append('w');
                            break;
                        case 's':
                            world.moveAvatar(0, -1);
                            world.history.append('s');
                            break;
                        case 'a':
                            world.moveAvatar(-1, 0);
                            world.history.append('a');
                            break;
                        case 'd':
                            world.moveAvatar(1, 0);
                            world.history.append('d');
                            break;
                        case 'e':
                            world.lightOn = !world.lightOn;
                            world.history.append('e');
                            break;
                        case 'q':
                            System.exit(0);
                            break;
                        case ':':
                            try {
                                File file = new File("save.txt");
                                PrintWriter out = new PrintWriter(file);
                                out.println(seed);
                                out.println(world.history.toString());
                                out.close();
                            } catch (Exception e) {
                                System.out.println("保存失败惹： " + e.getMessage());
                            }
                            break;
                        default:
                            break;
                    }
                }
                if (world.isWin || world.isOver) {
                    status = gameOver;
                }

                ter.resetFont();
                ter.renderFrame(world.renderWorld());

                StdDraw.setPenColor(StdDraw.WHITE);
                StdDraw.setFont(new Font(Font.MONOSPACED, Font.BOLD, 30));
                String stats = "Health: " + world.health + " | Coins: " + world.coins;
                StdDraw.text(30, World.HEIGHT + 2, stats);
            }

            if (status == gameOver) {
                if (world.isWin) {
                    StdDraw.setPenColor(StdDraw.YELLOW);
                    StdDraw.setFont(new Font(Font.MONOSPACED, Font.BOLD, 70));
                    StdDraw.text(30, 30, "VICTORY!");

                    StdDraw.setPenColor(StdDraw.WHITE);
                    StdDraw.setFont(new Font(Font.MONOSPACED, Font.BOLD, 50));
                    StdDraw.text(30, 20, "score: " + world.score());
                }

                if (world.isOver) {
                    StdDraw.setPenColor(StdDraw.BLUE);
                    StdDraw.setFont(new Font(Font.MONOSPACED, Font.BOLD, 70));
                    StdDraw.text(30, 30, "LOSE...");

                    StdDraw.setPenColor(StdDraw.WHITE);
                    StdDraw.setFont(new Font(Font.MONOSPACED, Font.BOLD, 50));
                    StdDraw.text(30, 20, "score: " + world.score());
                }

                StdDraw.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 30));
                StdDraw.text(30, 10, "(N) New game");
                StdDraw.text(30, 8, "(Q) Quit game");

                if (StdDraw.hasNextKeyTyped()) {
                    char c = StdDraw.nextKeyTyped();
                    c = Character.toLowerCase(c);

                    switch (c) {
                        case 'n':
                            status = seedInput;
                            seedString = "";
                            break;
                        case 'q':
                            System.exit(0);
                            break;
                        default:
                            break;
                    }
                }

            }


            StdDraw.show();
            StdDraw.pause(20);
        }
    }
}
