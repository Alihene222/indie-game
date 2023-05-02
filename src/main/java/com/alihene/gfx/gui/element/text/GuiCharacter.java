package com.alihene.gfx.gui.element.text;

import com.alihene.gfx.RenderSystem;
import com.alihene.gfx.gui.element.GuiImage;
import org.joml.Vector2f;
import org.joml.Vector2i;

public class GuiCharacter {
    private final float advance;
    private final Vector2i texturePos;
    private final Vector2i textureSize;

    public GuiCharacter(float advance, Vector2i texturePos, Vector2i textureSize) {
        this.advance = advance;
        this.texturePos = texturePos;
        this.textureSize = textureSize;
    }

    public GuiImage image(RenderSystem renderSystem, Vector2f pos, float size) {
        GuiImage image = new GuiImage(pos, new Vector2f(size, size));
        image.setTexture(renderSystem.getTextureByName("text"));
        image.texturePos = this.texturePos;
        image.textureSize = this.textureSize;
        return image;
    }

    public float getAdvance() {
        return advance;
    }

    public Vector2i getTexturePos() {
        return texturePos;
    }
}
