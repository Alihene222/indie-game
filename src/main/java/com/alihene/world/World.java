package com.alihene.world;

import com.alihene.Game;
import com.alihene.Main;
import com.alihene.gfx.Animation;
import com.alihene.world.gameobject.Entity;
import com.alihene.world.gameobject.player.Player;
import com.alihene.world.gameobject.player.item.GrassSeedsItem;
import com.alihene.world.gameobject.tile.BarrierTile;
import com.alihene.world.gameobject.tile.RockTile;
import com.alihene.world.gameobject.tile.SoilTile;
import com.alihene.world.gameobject.tile.Tile;
import org.joml.Vector2f;

import java.util.Optional;

public class World implements Tickable {
    public Player player;
    public final EntityCollection entityCollection;
    public final TileMap tileMap;
    public final PlantCollection plantCollection;
    private final Game game;

    public World() {
        entityCollection = new EntityCollection();
        tileMap = new TileMap();
        plantCollection = new PlantCollection();
        game = Main.game;
    }

    public void init() {
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
                addTile(tile);
            }
        }

        player = new Player(new Vector2f(0.0f, 0.0f), new Vector2f(2.0f, 2.0f));
        player.setDefaultTexture(game.renderSystem.getTextureByName("player0"));
        player.useDefaultTexture();

        Animation animation = new Animation(player, 30);
        animation.addFrame(game.renderSystem.getTextureByName("player1"), 15);
        animation.addFrame(game.renderSystem.getTextureByName("player0"), 15);
        player.animations.add(animation);

        addEntity(player);

        player.getHotbar().addItem(GrassSeedsItem.class, 5);
    }

    @Override
    public void tick() {
        tileMap.tick();
        entityCollection.tick();
        plantCollection.tick();
    }

    public void render() {
        tileMap.render();
        plantCollection.render();
        entityCollection.render();
    }

    public void addEntity(Entity entity) {
        entityCollection.addEntity(entity);
    }

    public void addTile(Tile tile) {
        tileMap.addTile(tile);
    }

    public Optional<Tile> getTileAtCoordinates(float x, float y) {
        return tileMap.tiles.stream().filter(tile -> tile.pos.x == x && tile.pos.y == y).findFirst();
    }
}
