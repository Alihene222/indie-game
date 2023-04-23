package com.alihene.world.gameobject.tile;

import com.alihene.world.TileMap;
import com.alihene.world.gameobject.Gameobject;
import com.alihene.world.gfx.TileMesh;
import org.joml.Vector2f;
import org.joml.Vector2i;

public abstract class Tile extends Gameobject {
    protected TileMaterial material;
    protected Vector2i texCoords;

    public TileMap tileMap;
    public int tileMapIndex = -1;

    public TileMesh mesh;
    public int index = -1;

    public Tile(Vector2f pos) {
        super(pos, new Vector2f(2.0f, 2.0f));
    }

    public TileMaterial getMaterial() {
        return material;
    }

    public Vector2i getTexCoords() {
        return texCoords;
    }

    public void updateMesh() {
        mesh.meshAt(index);
    }

    public void changeTileAtPosition(Tile tile) {
        if(tileMapIndex < 0 || index < 0) {
            return;
        }

        tileMap.tiles.remove(tileMapIndex);
        mesh.tiles.remove(index);

        tileMap.addTile(tile);
    }

    public Vector2f getPlantPosition() {
        return new Vector2f(pos.x + 0.125f, pos.y + 0.125f);
    }
}
