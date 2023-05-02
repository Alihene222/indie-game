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

        for(int i = -100; i < 100; i += 2) {
            for(int j = -100; j < 100; j += 2) {
                Tile tile;
                if(i == -100 || i == 98 || j == -100 || j == 98) {
                    tile = new BarrierTile(new Vector2f(0.0f + i, 0.0f + j));
                    tile.setTexture(game.renderSystem.getTextureByName("barrier"));
                } else {
                    if(i == 0 && j == 0) {
                        tile = new SoilTile(new Vector2f(0.0f, 0.0f));
                        tile.setTexture(game.renderSystem.getTextureByName("soil"));
                    } else {
                        tile = new RockTile(new Vector2f(0.0f + i, 0.0f + j));
                        tile.setTexture(game.renderSystem.getTextureByName("rock"));
                    }
                }
                game.world.addTile(tile);
            }
        }

        game.world.player = new Player(new Vector2f(0.0f, 0.0f), new Vector2f(2.0f, 2.0f));
        game.world.player.setDefaultTexture(game.renderSystem.getTextureByName("player0"));
        game.world.player.useDefaultTexture();

        Animation animation = new Animation(game.world.player, 30);
        animation.addFrame(game.renderSystem.getTextureByName("player1"), 15);
        animation.addFrame(game.renderSystem.getTextureByName("player0"), 15);
        game.world.player.animations.add(animation);

        game.world.addEntity(game.world.player);

        game.world.player.getHotbar().addItem(GrassSeedsItem.class, 5);

        FrameTask task = new FrameTask(250);
        task.setAction(249, () -> {
            if(!game.world.plantCollection.plants.isEmpty()) {
                //TODO: try again if plant is already fully grown
                int index = (int) (Math.random() * game.world.plantCollection.plants.size());
                Plant plant = game.world.plantCollection.plants.get(index);
                plant.setTextureInfo(GrassPlant.FULL_GROWN_TEXTURE);
                plant.updateMesh();
            }
        });

        game.submitFrameTask(task);

        game.run();
    }
}
