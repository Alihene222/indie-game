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

    public void attrib(int index, int size, int type, int stride, long pointer) {
        glVertexAttribPointer(index, size, type, false, stride, pointer);
        glEnableVertexAttribArray(0);
    }
}
