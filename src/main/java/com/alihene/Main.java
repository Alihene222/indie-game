package com.alihene;

import com.alihene.gfx.Animation;
import com.alihene.util.FrameTask;
import com.alihene.util.Util;
import com.alihene.world.gameobject.plant.GrassPlant;
import com.alihene.world.gameobject.plant.Plant;
import com.alihene.world.gameobject.player.Player;
import com.alihene.world.gameobject.player.item.GrassSeedsItem;
import com.alihene.world.gameobject.tile.BarrierTile;
import com.alihene.world.gameobject.tile.RockTile;
import com.alihene.world.gameobject.tile.SoilTile;
import com.alihene.world.gameobject.tile.Tile;
import org.joml.Vector2f;

import java.util.concurrent.ThreadLocalRandom;

public class Main {
    public static Game game;

    public static void main(String[] args) {
        game = new Game();
        game.createWindow("Hello, World!", 640, 640, false);
        game.run();
    }
}
