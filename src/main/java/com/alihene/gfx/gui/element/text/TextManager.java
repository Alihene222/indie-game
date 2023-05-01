package com.alihene.gfx.gui.element.text;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.joml.Vector2i;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TextManager {
    private final Map<Character, GuiCharacter> characters;

    public TextManager() {
        characters = new HashMap<>();

        try(BufferedReader reader = new BufferedReader(new FileReader("res/font_info.json"))) {
            JsonArray array = JsonParser.parseReader(reader).getAsJsonArray();
            array.forEach(jsonElement -> {
                JsonObject charInfo = jsonElement.getAsJsonObject();
                JsonObject texturePos = charInfo.get("texturePos").getAsJsonObject();

                characters.put(charInfo.get("char").getAsString().charAt(0), new GuiCharacter(charInfo.get("advance").getAsFloat(), new Vector2i(texturePos.get("x").getAsInt(), texturePos.get("y").getAsInt()), new Vector2i(1, 1)));
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public GuiCharacter get(char c) {
        GuiCharacter character = characters.get(c);
        if(character == null) {
            if(Character.isUpperCase(c)) {
                return characters.get(Character.toLowerCase(c));
            } else {
                return characters.get(Character.toUpperCase(c));
            }
        }
        return characters.get(c);
    }
}
