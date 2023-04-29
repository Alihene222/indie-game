package com.alihene.gfx.gui.element.text;

import com.alihene.gfx.Texture;
import org.joml.Vector2f;
import org.lwjgl.util.freetype.FT_Face;

import java.util.Objects;

import static org.lwjgl.opengl.GL33.*;

public class CharacterTexture {
    private final int handle;

    public CharacterTexture(FT_Face face) {
        handle = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, handle);

        glTexImage2D(
                GL_TEXTURE_2D,
                0,
                GL_RED,
                Objects.requireNonNull(face.glyph()).bitmap().width(),
                face.glyph().bitmap().rows(),
                0,
                GL_RED,
                GL_UNSIGNED_BYTE,
                face.glyph().bitmap().buffer(1)
        );
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

    public static Vector2f[] getTexCoords() {
        return new Vector2f[] {
            new Vector2f(0.0f, 1.0f),
            new Vector2f(1.0f, 1.0f),
            new Vector2f(1.0f, 0.0f),
            new Vector2f(0.0f, 0.0f)
        };
    }
}
