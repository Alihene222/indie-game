package com.alihene.gfx.gui.element.text;

import com.alihene.gfx.Texture;
import org.joml.Vector2i;

public class GuiCharacter {
    private CharacterTexture texture;
    private Vector2i size, bearing;
    private long advance;

    public CharacterTexture getTexture() {
        return texture;
    }

    public void setTexture(CharacterTexture texture) {
        this.texture = texture;
    }

    public Vector2i getSize() {
        return size;
    }

    public void setSize(Vector2i size) {
        this.size = size;
    }

    public Vector2i getBearing() {
        return bearing;
    }

    public void setBearing(Vector2i bearing) {
        this.bearing = bearing;
    }

    public long getAdvance() {
        return advance;
    }

    public void setAdvance(long advance) {
        this.advance = advance;
    }
}
