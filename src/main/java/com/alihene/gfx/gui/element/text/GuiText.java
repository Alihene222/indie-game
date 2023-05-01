package com.alihene.gfx.gui.element.text;

import com.alihene.Main;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class GuiText {
    private final float size;
    private final Vector2f pos;
    private String string;

    public final List<GuiCharacter> characters;

    private Alignment alignment = Alignment.LEFT;

    public GuiText(Vector2f pos, float size) {
        this.size = size;
        this.pos = pos;
        characters = new ArrayList<>();
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
        updateCharacters();
    }

    public float getSize() {
        return size;
    }

    public Vector2f getPos() {
        return pos;
    }

    public Alignment getAlignment() {
        return alignment;
    }

    public void setAlignment(Alignment alignment) {
        this.alignment = alignment;
    }

    private void updateCharacters() {
        characters.clear();
        for(char c : string.toCharArray()) {
            characters.add(Main.game.renderSystem.textManager.get(c));
        }
    }

    public enum Alignment {
        LEFT,
        RIGHT
    }
}