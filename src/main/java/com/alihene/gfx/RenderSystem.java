package com.alihene.gfx;

import com.alihene.Main;
import com.alihene.gfx.gui.GuiCollection;
import com.alihene.gfx.gui.element.GuiSelector;
import com.alihene.gfx.util.Camera;
import com.alihene.util.Util;
import com.alihene.world.Tickable;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.stb.STBImage.stbi_set_flip_vertically_on_load;

public class RenderSystem implements Tickable {
    public static final int[] TEX_SLOTS = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};

    public static final int ENTITY_SHADER = 0;
    public static final int TILE_SHADER = 1;
    public static final int GUI_SHADER = 2;
    public static final int PLANT_SHADER = 3;

    public final Window window;

    private final Camera camera;

    private final Shader[] shaders;

    public GuiCollection guiCollection;

    private final TextureSystem textureSystem;

    public RenderSystem(Window window) {
        this.window = window;

        shaders = new Shader[4];
        shaders[ENTITY_SHADER] = new Shader("res/shaders/entity.vert", "res/shaders/entity.frag");
        shaders[TILE_SHADER] = new Shader("res/shaders/tile.vert", "res/shaders/tile.frag");
        shaders[GUI_SHADER] = new Shader("res/shaders/gui.vert", "res/shaders/gui.frag");
        shaders[PLANT_SHADER] = new Shader("res/shaders/plant.vert", "res/shaders/plant.frag");

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
        // No interpolation due to the game being pixel based
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        glEnable(GL_MULTISAMPLE);

        glEnable(GL_BLEND);
        glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_ALPHA);

        stbi_set_flip_vertically_on_load(true);

        camera = new Camera();
        camera.adjustProjection();

        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        textureSystem = new TextureSystem();
        textureSystem.loadTextures();
    }

    public void render() {
        glClear(GL_COLOR_BUFFER_BIT);

        Main.game.world.render();
        guiCollection.render();

        glfwSwapBuffers(window.getHandle());
        glfwPollEvents();
    }

    public void initGui() {
        guiCollection = new GuiCollection(this);
        guiCollection.init();
    }

    public Camera getCamera() {
        return camera;
    }

    public Shader getShader(int shader) {
        return shaders[shader];
    }

    public Texture getTextureByName(String name) {
        return textureSystem.getTextureByName(name);
    }

    @Override
    public void tick(float delta) {
        camera.tick(delta);
        guiCollection.tick(delta);
    }
}

class TextureSystem {
    private final List<Texture> textures;

    public TextureSystem() {
        textures = new ArrayList<>();
    }

    public void loadTextures() {
        String json = Util.readFile("res/textures/texture_info.json");
        JsonArray root = JsonParser.parseString(json).getAsJsonArray();
        root.forEach(element -> {
            JsonObject textureJson = element.getAsJsonObject();

            textures.add(new Texture(
                    textureJson.get("name").getAsString(),
                    "res/textures/" + textureJson.get("path").getAsString()));
        });
    }

    public Texture getTextureByName(String name) {
        return textures.stream().filter(texture -> texture.getName().equals(name)).findFirst().orElseThrow();
    }
}
