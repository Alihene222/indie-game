package com.alihene.gfx;

import com.alihene.Main;
import com.alihene.gfx.gui.element.GuiSelector;
import com.alihene.util.Mode;
import com.alihene.util.Util;
import com.alihene.world.gameobject.plant.GrassPlant;
import com.alihene.world.gameobject.player.item.GrassSeedsItem;
import com.alihene.world.gameobject.tile.SoilTile;
import org.joml.Vector2f;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWFramebufferSizeCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL33;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
    private final long handle;
    private final Vector2f dimensions;
    private float aspectRatio;

    private final Vector2f mousePosition;

    public Window(String name, int width, int height, boolean resizable) {
        if(!glfwInit()) {
            throw new IllegalStateException("GLFW initialization failed");
        }

        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_RESIZABLE, resizable ? GLFW_TRUE : GLFW_FALSE);
        glfwWindowHint(GLFW_SAMPLES, 4);
        handle = glfwCreateWindow(width, height, name, NULL, NULL);

        dimensions = new Vector2f(640.0f, 640.0f);
        aspectRatio = (float) width / (float) height;

        mousePosition = new Vector2f(0.0f, 0.0f);

        glfwSetFramebufferSizeCallback(handle, new GLFWFramebufferSizeCallback() {
            @Override
            public void invoke(long l, int i, int i1) {
                aspectRatio = (float) i / (float) i1;
                GL33.glViewport(0, 0, i, i1);
            }
        });

        glfwSetCursorPosCallback(handle, new GLFWCursorPosCallback() {
            @Override
            public void invoke(long l, double xPos, double yPos) {
                mousePosition.x = (float) xPos;
                mousePosition.y = (float) yPos;
            }
        });

        glfwSetMouseButtonCallback(handle, new GLFWMouseButtonCallback() {
            @Override
            public void invoke(long l, int button, int action, int mods) {
                if(button == GLFW_MOUSE_BUTTON_LEFT && action == GLFW_PRESS) {
                    GuiSelector selector = Main.game.renderSystem.guiCollection.selector;

                    selector.getTile().ifPresent(tile -> {
                        if (tile.getClass() == SoilTile.class) {
                            if((float) Math.sqrt(((float) Math.pow(Util.differenceBetween(tile.pos.x + 1.0f, Main.game.world.player.pos.x + 1.0f), 2)) + (float) Math.pow(Util.differenceBetween(tile.pos.y + 1.0f, Main.game.world.player.pos.y + 1.0f), 2)) < 7.5f) {
                                if (((SoilTile) tile).hasPlant()) {
                                    ((SoilTile) tile).removePlant();
                                }
                            }
                        }
                    });
                } else if(button == GLFW_MOUSE_BUTTON_RIGHT && action == GLFW_PRESS) {
                    GuiSelector selector = Main.game.renderSystem.guiCollection.selector;

                    selector.getTile().ifPresent(tile -> {
                        if (tile.getClass() == SoilTile.class) {
                            if((float) Math.sqrt(((float) Math.pow(Util.differenceBetween(tile.pos.x + 1.0f, Main.game.world.player.pos.x + 1.0f), 2)) + (float) Math.pow(Util.differenceBetween(tile.pos.y + 1.0f, Main.game.world.player.pos.y + 1.0f), 2)) < 7.5f) {
                                if (!((SoilTile) tile).hasPlant()) {
                                    if (Main.game.world.player.getHotbar().hasItem(GrassSeedsItem.class)) {
                                        Main.game.world.player.getHotbar().decrement(GrassSeedsItem.class);
                                        ((SoilTile) tile).setPlant(GrassPlant.class);
                                    }
                                }
                            }
                        }
                    });
                }
            }
        });

        glfwSetKeyCallback(handle, new GLFWKeyCallback() {
            @Override
            public void invoke(long win, int key, int scancode, int action, int mods) {
                if(key == GLFW_KEY_F && action == GLFW_PRESS) {
                    Main.game.renderSystem.guiCollection.fpsIndicator.updateString("Text has changed! Oh no!");
                }
            }
        });

        if(handle == NULL) {
            throw new IllegalStateException("Window creation failed");
        }

        glfwShowWindow(handle);
    }

    public void initOpenGL() {
        glfwMakeContextCurrent(handle);
        glfwSwapInterval(0);
        GL.createCapabilities();
    }

    public boolean shouldClose() {
        return glfwWindowShouldClose(handle);
    }

    public void terminate() {
        glfwFreeCallbacks(handle);
        glfwDestroyWindow(handle);
        glfwTerminate();
    }

    public boolean keyPressed(int key) {
        return glfwGetKey(handle, key) == GLFW_PRESS;
    }

    public void updateCursor(Mode mode) {
        switch (mode) {
            case NORMAL:
                glfwSetInputMode(handle, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
                break;
            case BUILDING:
            case PLANTING:
                glfwSetInputMode(handle, GLFW_CURSOR, GLFW_CURSOR_HIDDEN);
                break;
        }
    }

    public long getHandle() {
        return handle;
    }

    public Vector2f getDimensions() {
        return dimensions;
    }

    public float getAspectRatio() {
        return aspectRatio;
    }

    public Vector2f getMousePosition() {
        return mousePosition;
    }
}