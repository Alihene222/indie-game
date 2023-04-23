package com.alihene.world.gameobject.tile;

import com.alihene.world.gameobject.plant.Plant;
import org.joml.Vector2f;

public class RockTile extends Tile {
    public Plant plant;

    public RockTile(Vector2f pos) {
        super(pos);
        material = TileMaterial.ROCK;
    }

    @Override
    public void tick(float delta) {

    }
}
