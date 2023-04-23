package com.alihene.world.gameobject.plant;

import com.alihene.world.PlantCollection;
import com.alihene.world.gameobject.Gameobject;
import com.alihene.world.gfx.PlantMesh;
import org.joml.Vector2f;

public abstract class Plant extends Gameobject {
    public PlantMesh mesh;
    public int index = -1;
    public PlantCollection plantCollection;

    public Plant(Vector2f pos, Vector2f size) {
        super(pos, size);
    }

    @Override
    public void updateMesh() {

    }
}
