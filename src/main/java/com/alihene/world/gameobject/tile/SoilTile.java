package com.alihene.world.gameobject.tile;

import com.alihene.Main;
import com.alihene.world.gameobject.plant.Plant;
import org.joml.Vector2f;

public class SoilTile extends Tile {
    private Plant plant;

    public SoilTile(Vector2f pos) {
        super(pos);
    }

    @Override
    public void tick(float delta) {

    }

    public Plant getPlant() {
        return plant;
    }

    public void setPlant(Plant plant) {
        this.plant = plant;
        Main.game.world.plantCollection.addPlant(plant);
    }

    public void removePlant() {
        plant.plantCollection.plants.remove(plant);
        plant.mesh.plants.remove(plant.index);
        plant.mesh.mesh();
        plant = null;
    }

    public boolean hasPlant() {
        return plant != null;
    }
}
