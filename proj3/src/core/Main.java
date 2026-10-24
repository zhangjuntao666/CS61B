package core;

import tileengine.TERenderer;

public class Main {
    public static void main(String[] args) {
        // build your own world!
        int hudHeight = 5;

        TERenderer ter = new TERenderer();
        ter.initialize(World.WIDTH, World.HEIGHT + hudHeight);

        /*catch from user*/
        long seed = 1997;
        World world = new World(seed);

        ter.renderFrame(world.world);

    }
}
