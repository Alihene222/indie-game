package com.alihene.gfx;

import static org.lwjgl.opengl.GL33.*;

public class IndexBuffer {
    private final int handle;

    public IndexBuffer() {
        handle = glGenBuffers();
    }

    public void bind() {
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, handle);
    }

    public void unbind() {
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    public void buffer(int[] indices) {
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);
    }
}
