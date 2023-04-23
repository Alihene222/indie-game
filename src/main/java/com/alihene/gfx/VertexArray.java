package com.alihene.gfx;

import static org.lwjgl.opengl.GL33.*;

public class VertexArray {
    private final int handle;

    public VertexArray() {
        handle = glGenVertexArrays();
    }

    public void bind() {
        glBindVertexArray(handle);
    }

    public void unbind() {
        glBindVertexArray(0);
    }
}
