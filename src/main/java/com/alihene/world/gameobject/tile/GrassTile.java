package com.alihene.world.gameobject.tile;

import org.joml.Vector2f;

public class GrassTile extends Tile {
    public GrassTile(Vector2f pos) {
        super(pos);
        material = TileMaterial.GRASS;
    }

    @Override
    public void tick(float delta) {

    }
}
