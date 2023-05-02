package com.alihene.world.gameobject.plant;

import com.alihene.Main;
import com.alihene.gfx.Texture;
import org.joml.Vector2f;
import org.joml.Vector2i;

public class GrassPlant extends Herb {
    public static Texture.Info SEEDS_TEXTURE = new Texture.Info(new Vector2i(2, 0), new Vector2i(1, 1), new Vector2i(16, 16));
    public static Texture.Info HALF_GROWN_TEXTURE = new Texture.Info(new Vector2i(1, 0), new Vector2i(1, 1), new Vector2i(16, 16));
    public static Texture.Info FULL_GROWN_TEXTURE = new Texture.Info(new Vector2i(0, 0), new Vector2i(1, 1), new Vector2i(16, 16));

    public GrassPlant(Vector2f pos, Vector2f size) {
        super(pos, size);
        setTexture(Main.game.renderSystem.getTextureByName("plants"));
    }

    @Override
    public void tick() {

    }
}
