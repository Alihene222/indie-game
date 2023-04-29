package com.alihene.gfx;

import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.stb.STBImage.*;

public class Texture {
    private int handle;
    private final String name;

    public Texture(String name, String filePath) {
        this(name, filePath, Mode.RGBA);
    }

    public Texture(String name, String filePath, Mode mode) {
        handle = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, handle);

        IntBuffer width = BufferUtils.createIntBuffer(1);
        IntBuffer height = BufferUtils.createIntBuffer(1);
        IntBuffer channels = BufferUtils.createIntBuffer(1);
        ByteBuffer image = stbi_load(filePath, width, height, channels, 4);

        if(image != null) {
            switch(mode) {
                case RGBA:
                    glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width.get(0), height.get(0), 0, GL_RGBA, GL_UNSIGNED_BYTE, image);
                    break;
                case RGB:
                    glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width.get(0), height.get(0), 0, GL_RGB, GL_UNSIGNED_BYTE, image);
                    break;
                case RED:
                    glTexImage2D(GL_TEXTURE_2D, 0, GL_RED, width.get(0), height.get(0), 0, GL_RED, GL_UNSIGNED_BYTE, image);
                    break;
            }
            glGenerateMipmap(GL_TEXTURE_2D);

        } else {
            throw new RuntimeException("Could not load image from path " + filePath);
        }

        stbi_image_free(image);

        this.name = name;
    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, handle);
    }

    public void unbind() {
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    public void activate(int offset) {
        glActiveTexture(GL_TEXTURE0 + offset);
    }

    public int getHandle() {
        return handle;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if(o == this) {
            return true;
        }

        if (!(o instanceof Texture)) {
            return false;
        }

        Texture t = (Texture) o;

        return Float.compare(t.handle, this.handle) == 0;
    }

    enum Mode {
        RGB,
        RGBA,
        RED
    }
}
