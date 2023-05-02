package com.alihene.gfx;

import static org.lwjgl.opengl.GL33.*;

public class VertexBuffer {
    private final int handle;

    public VertexBuffer() {
        handle = glGenBuffers();
    }

    public void bind() {
        glBindBuffer(GL_ARRAY_BUFFER, handle);
    }

    public void buffer(float[] data, boolean dynamic) {
        glBufferData(GL_ARRAY_BUFFER, data, dynamic ? GL_DYNAMIC_DRAW : GL_STATIC_DRAW);
    }

    public void buffer(long count, boolean dynamic) {
        glBufferData(GL_ARRAY_BUFFER, count * Float.BYTES, dynamic ? GL_DYNAMIC_DRAW : GL_STATIC_DRAW);
    }

    public void bufferSub(float[] data) {
        glBufferSubData(GL_ARRAY_BUFFER, 0, data);
    }
}