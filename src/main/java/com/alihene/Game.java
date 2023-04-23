package com.alihene;

import com.alihene.gfx.RenderSystem;
import com.alihene.gfx.Window;
import com.alihene.util.Mode;
import com.alihene.world.World;

import static org.lwjgl.glfw.GLFW.*;

public class Game {
    public Window window;
    public RenderSystem renderSystem;
    public World world;

    public Mode mode;

    public void createWindow(String name, int width, int height, boolean resizable) {
        window = new Window(name, width, height, resizable);
        window.initOpenGL();

        renderSystem = new RenderSystem(window);
        renderSystem.initGui();
        world = new World();
        mode = Mode.NORMAL;
    }

    private void update() {
        world.update();
    }

    private void tick(float delta) {
        world.tick(delta);
        renderSystem.tick(delta);
    }

    public void run() {
        float delta;
        float lastFrame = 0.0f;

        while(!window.shouldClose()) {
            float currentFrame = (float) glfwGetTime();
            delta = currentFrame - lastFrame;
            lastFrame = currentFrame;

            update();
            tick(delta);
            renderSystem.render();
        }

        window.terminate();
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
        window.updateCursor(mode);
    }
}