package com.alihene.world.gameobject.plant;

import com.alihene.Main;
import org.joml.Vector2f;

public class GrassPlant extends Herb {
    public GrassPlant(Vector2f pos, Vector2f size) {
        super(pos, size);
        setTexture(Main.game.renderSystem.getTextureByName("grass"));
    }

    @Override
    public void tick() {

    }
}
