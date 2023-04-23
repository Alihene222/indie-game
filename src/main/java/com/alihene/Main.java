package com.alihene;

import com.alihene.gfx.Animation;
import com.alihene.world.gameobject.plant.GrassPlant;
import com.alihene.world.gameobject.plant.Plant;
import com.alihene.world.gameobject.player.Player;
import com.alihene.world.gameobject.tile.BarrierTile;
import com.alihene.world.gameobject.tile.RockTile;
import com.alihene.world.gameobject.tile.SoilTile;
import com.alihene.world.gameobject.tile.Tile;
import org.joml.Vector2f;

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

        SoilTile tile = (SoilTile) game.world.tileMap.tiles.stream().filter(t -> t.pos.x == 0.0f && t.pos.y == 0.0f).findFirst().orElseThrow();
        Plant plant = new GrassPlant(new Vector2f(0.125f, 0.125f), new Vector2f(1.75f, 1.75f));
        tile.setPlant(plant);

        game.run();
    }
}
