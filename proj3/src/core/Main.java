package core;

import tileengine.TERenderer;
import tileengine.TETile;

public class Main {
    public static void main(String[] args) {
        // build your own world!
        int hudHeight = 5;

        TERenderer ter = new TERenderer();
        ter.initialize(World.WIDTH, World.HEIGHT + hudHeight);

        /*catch from user*/
        long seed = 1990;
        World world = new World(seed);

        boolean lightOn = true;

        TETile[][] renderWorld = world.renderWorld(lightOn);

        ter.renderFrame(renderWorld);

    }
}
