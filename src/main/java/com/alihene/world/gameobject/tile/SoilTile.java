package com.alihene.world.gameobject.tile;

import com.alihene.Main;
import com.alihene.world.gameobject.plant.Plant;
import org.joml.Vector2f;

import java.lang.reflect.InvocationTargetException;

public class SoilTile extends Tile {
    private Plant plant;

    public SoilTile(Vector2f pos) {
        super(pos);
    }

    @Override
    public void tick() {

    }

    public Plant getPlant() {
        return plant;
    }

    public void setPlant(Plant plant) {
        this.plant = plant;
        Main.game.world.plantCollection.addPlant(plant);
    }

    public void setPlant(Class<? extends Plant> plant) {
        try {
            Plant p = plant.getConstructor(Vector2f.class, Vector2f.class).newInstance(new Vector2f(pos.x + 0.125f, pos.y + 0.125f), new Vector2f(1.75f, 1.75f));
            setPlant(p);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            throw new RuntimeException(e);
        }
    }

    public void removePlant() {
        plant.plantCollection.plants.remove(plant);
        plant.mesh.plants.remove(plant.index);
        plant.mesh.plantCount--;
        plant.mesh.mesh();
        plant = null;
    }

    public boolean hasPlant() {
        return plant != null;
    }
}
