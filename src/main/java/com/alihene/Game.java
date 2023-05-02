package com.alihene;

import com.alihene.gfx.RenderSystem;
import com.alihene.gfx.Window;
import com.alihene.util.FrameTask;
import com.alihene.util.Mode;
import com.alihene.world.World;

import java.util.ArrayList;
import java.util.List;

public class Game {
    public Window window;
    public RenderSystem renderSystem;
    public World world;

    public Mode mode;

    private List<FrameTask> frameTasks;

    public void createWindow(String name, int width, int height, boolean resizable) {
        window = new Window(name, width, height, resizable);
        window.initOpenGL();

        renderSystem = new RenderSystem(window);
        renderSystem.initGui();
        world = new World();
        mode = Mode.NORMAL;

        frameTasks = new ArrayList<>();
    }

    private void tick() {
        world.tick();
        renderSystem.tick();
        for(FrameTask frameTask : frameTasks) {
            frameTask.tick();
        }
    }

    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int updates = 0;
        int frames = 0;

        while(!window.shouldClose()) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            while(delta >= 1) {
                tick();
                updates++;
                delta--;
            }

            renderSystem.render();
            frames++;

            if(System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                renderSystem.guiCollection.fpsIndicator.updateString("FPS: " + frames);
                frames = 0;
                updates = 0;
            }
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

    public void submitFrameTask(FrameTask task) {
        frameTasks.add(task);
    }
}