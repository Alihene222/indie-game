package com.alihene.world.gameobject;

import com.alihene.world.gfx.EntityMesh;
import org.joml.Vector2f;

public abstract class Entity extends Gameobject {
    private int health;

    public EntityMesh mesh;
    public int index = -1;

    public Entity(Vector2f pos, Vector2f size) {
        super(pos, size);
        health = 0;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void updateMesh() {
        mesh.meshAt(index);
    }
}
