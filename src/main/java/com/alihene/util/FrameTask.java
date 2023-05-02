package com.alihene.util;

import com.alihene.world.Tickable;

public class FrameTask implements Tickable {
    private final int size;
    private final Action[] actions;
    private int framesTicked = 0;

    public FrameTask(int size) {
        this.size = size;
        actions = new Action[size];
    }

    public void setAction(int index, Action action) {
        actions[index] = action;
    }

    @Override
    public void tick() {
        if(framesTicked == size) {
            framesTicked = 0;
        }

        if(actions[framesTicked] != null) {
            actions[framesTicked].execute();
        }
        framesTicked++;
    }

    public interface Action {
        void execute();
    }
}
