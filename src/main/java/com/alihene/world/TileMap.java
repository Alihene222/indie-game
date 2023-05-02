package com.alihene.world;

import com.alihene.Main;
import com.alihene.gfx.RenderSystem;
import com.alihene.world.gameobject.tile.Tile;
import com.alihene.world.gfx.TileMesh;

import java.util.ArrayList;
import java.util.List;

public class TileMap implements Tickable {
    public final List<Tile> tiles;
    private final List<TileMesh> meshes;

    public TileMap() {
        tiles = new ArrayList<>();
        meshes = new ArrayList<>();
    }

    public void addTile(Tile tile) {
        tiles.add(tile);
        addTileToMesh(tile);
    }

    private void addTileToMesh(Tile tile) {
        boolean added = false;

        for(TileMesh mesh : meshes) {
            if(mesh.tileCount < TileMesh.TILE_MESH_MAX_SIZE) {
                if(mesh.textures.size() < 16 || mesh.textures.contains(tile.getTexture())) {
                    mesh.addTile(tile);
                    tile.mesh = mesh;
                    tile.index = mesh.tileCount - 1;
                    mesh.meshAt(tile.index);
                    added = true;
                    break;
                }
            }
        }

        if(!added) {
            TileMesh newMesh = new TileMesh();
            newMesh.setShader(Main.game.renderSystem.getShader(RenderSystem.TILE_SHADER));
            newMesh.addTile(tile);
            tile.mesh = newMesh;
            tile.index = 0;
            newMesh.meshAt(0);
            newMesh.prepareRender();
            meshes.add(newMesh);
        }
    }

    public void render() {
        for(TileMesh mesh : meshes) {
            mesh.render();
        }
    }

    public boolean exists(Tile tile) {
        return tiles.contains(tile);
    }

    @Override
    public void tick() {
        for(Tile tile : tiles) {
            tile.tick();
        }
    }
}
