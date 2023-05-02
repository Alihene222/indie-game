package com.alihene.world;

import com.alihene.world.gameobject.Entity;
import com.alihene.world.gameobject.player.Player;
import com.alihene.world.gameobject.tile.Tile;

import java.util.Optional;

public class World implements Tickable {
    public Player player;
    public final EntityCollection entityCollection;
    public final TileMap tileMap;
    public final PlantCollection plantCollection;

    public World() {
        entityCollection = new EntityCollection();
        tileMap = new TileMap();
        plantCollection = new PlantCollection();
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
