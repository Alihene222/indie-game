package com.alihene.world.gameobject.tile;

import org.joml.Vector2f;

public class BarrierTile extends Tile {
    public BarrierTile(Vector2f pos) {
        super(pos);
        material = TileMaterial.ROCK;
    }

    @Override
    public void tick(float delta) {

    }
}
