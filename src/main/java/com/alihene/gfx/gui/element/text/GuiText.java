package com.alihene.gfx.gui.element.text;

import com.alihene.gfx.gui.element.GuiElement;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector3f;
import org.lwjgl.PointerBuffer;
import org.lwjgl.stb.STBTruetype;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.util.freetype.FT_Face;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.util.freetype.FreeType.*;
import static org.lwjgl.opengl.GL33.*;

public class GuiText {
    private final float size;
    private final Vector2f pos;
    private final Map<Character, GuiCharacter> characters;
    private String string;
    private Vector3f color;

    public GuiText(Vector2f pos, float size) {
        this.size = size;
        this.pos = pos;

        characters = new HashMap<>();

        PointerBuffer library = MemoryUtil.memAllocPointer(1);
        if(FT_Init_FreeType(library) != 0) {
            System.out.println("FreeType initialization failed");
            System.exit(-1);
        }

        PointerBuffer facePointer = MemoryUtil.memAllocPointer(1);
        if(FT_New_Face(library.get(0), "res/fonts/arial.ttf", 0, facePointer) != 0) {
            System.out.println("FreeType font loading failed");
            System.exit(-1);
        }

        FT_Face face = FT_Face.create(facePointer.get(0));

        FT_Set_Pixel_Sizes(face, 0, 48);

        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);

        for (char c = 0; c < 128; c++) {
            if (FT_Load_Char(face, c, FT_LOAD_RENDER) != 0) {
                System.out.println("FreeType failed to load glyph");
                continue;
            }

            CharacterTexture texture = new CharacterTexture(face);

            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

            GuiCharacter character = new GuiCharacter();
            character.setTexture(texture);
            character.setSize(new Vector2i(face.glyph().bitmap().width(), face.glyph().bitmap().rows()));
            character.setBearing(new Vector2i(face.glyph().bitmap_left(), face.glyph().bitmap_top()));
            character.setAdvance(face.glyph().advance().x());

            characters.put(c, character);
        }

        FT_Done_Face(face);
        FT_Done_FreeType(library.get(0));
    }

    public Map<Character, GuiCharacter> getCharacters() {
        return characters;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public float getSize() {
        return size;
    }

    public Vector2f getPos() {
        return pos;
    }

    public Vector3f getColor() {
        return color;
    }

    public void setColor(Vector3f color) {
        this.color = color;
    }
}