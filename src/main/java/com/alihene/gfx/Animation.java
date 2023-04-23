package com.alihene.gfx;

import com.alihene.world.gameobject.Gameobject;

public class Animation {
    private final Gameobject gameobject;

    private final Frame[] frames;
    private int frameCount = 0;

    private int framesTicked = 0;

    public Animation(Gameobject gameobject, int frames) {
        this.gameobject = gameobject;
        this.frames = new Frame[frames];
    }

    public void addFrame(Texture texture, int count) {
        Frame frame = new Frame(texture);
        for(int i = frameCount; i < frameCount + count; i++) {
            frames[i] = frame;
        }
        frameCount += count;
    }

    public void update() {
        if(framesTicked == frameCount) {
            framesTicked = 0;
        }
        gameobject.setTexture(frames[framesTicked].getTexture());
        gameobject.updateMesh();
        framesTicked++;
    }

    public void resetFrames() {
        framesTicked = 0;
    }
}

class Frame {
    private final Texture texture;

    public Frame(Texture texture) {
        this.texture = texture;
    }

    public Texture getTexture() {
        return texture;
    }
}