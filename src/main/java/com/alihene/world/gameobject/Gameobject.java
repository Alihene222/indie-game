package com.alihene.world.gameobject;

import com.alihene.gfx.Animation;
import com.alihene.gfx.Texture;
import com.alihene.world.Tickable;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

public abstract class Gameobject implements Tickable {
    public Vector2f pos;
    public Vector2f size;
    private float rotationAngle;
    private Texture texture;
    private Texture defaultTexture;
    private Texture.Info textureInfo = new Texture.Info();

    public Animation animation = null;
    public final List<Animation> animations;

    public Gameobject(Vector2f pos, Vector2f size) {
        this.pos = pos;
        this.size = size;
        rotationAngle = 0.0f;
        animations = new ArrayList<>();
    }

    public Vector2f getPos() {
        return pos;
    }

    public float getRotationAngle() {
        return rotationAngle;
    }

    public void setRotationAngle(float rotationAngle) {
        this.rotationAngle = rotationAngle;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public Texture getDefaultTexture() {
        return defaultTexture;
    }

    public void setDefaultTexture(Texture texture) {
        this.defaultTexture = texture;
    }

    public void useDefaultTexture() {
        texture = defaultTexture;
    }

    public Texture.Info getTextureInfo() {
        return textureInfo;
    }

    public void setTextureInfo(Texture.Info textureInfo) {
        this.textureInfo = textureInfo;
    }

    @Override
    public void tick() {
        if(animation != null) {
            animation.update();
        }
    }

    public abstract void updateMesh();
}
