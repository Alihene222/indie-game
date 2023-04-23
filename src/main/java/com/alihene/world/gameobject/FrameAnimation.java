package com.alihene.world.gameobject;

import com.alihene.gfx.Texture;

import java.util.HashMap;
import java.util.Map;

public class FrameAnimation {
    private int frames;
    private final Map<Integer, Texture> frameData;

    public FrameAnimation() {
        frameData = new HashMap<>();
    }

}
