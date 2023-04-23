package com.alihene.world.gameobject.player;

import com.alihene.Main;
import com.alihene.gfx.Window;
import com.alihene.world.gameobject.Entity;
import com.alihene.world.gameobject.tile.BarrierTile;
import com.alihene.world.gameobject.tile.Tile;
import org.joml.Vector2f;

import java.util.List;

import static org.lwjgl.glfw.GLFW.*;

public class Player extends Entity {
    public Player(Vector2f pos, Vector2f size) {
        super(pos, size);
        setHealth(10);
    }

    @Override
    public void tick(float delta) {
        if(mesh != null && index > -1) {
            boolean anyPressed = false;

            float speed = 10.0f;

            Window window = Main.game.window;

            if(window.keyPressed(GLFW_KEY_SPACE)) {
                speed *= 2.0f;
            }

            if (window.keyPressed(GLFW_KEY_W)) {
                anyPressed = true;
                pos.y += speed * delta;
                if(collidingWithBarrier()) {
                    while(collidingWithBarrier()) {
                        pos.y -= delta;
                    }
                }
            }
            if (window.keyPressed(GLFW_KEY_S)) {
                anyPressed = true;
                pos.y -= speed * delta;
                if(collidingWithBarrier()) {
                    while(collidingWithBarrier()) {
                        pos.y += delta;
                    }
                }
            }
            if (window.keyPressed(GLFW_KEY_A)) {
                anyPressed = true;
                pos.x -= speed * delta;
                if(collidingWithBarrier()) {
                    while(collidingWithBarrier()) {
                        pos.x += delta;
                    }
                }
            }
            if (window.keyPressed(GLFW_KEY_D)) {
                anyPressed = true;
                pos.x += speed * delta;
                if(collidingWithBarrier()) {
                    while(collidingWithBarrier()) {
                        pos.x -= delta;
                    }
                }
            }

            if(anyPressed) {
                animation = animations.get(0);
            } else {
                animation = null;
                animations.get(0).resetFrames();
                useDefaultTexture();
            }

            updateMesh();
        }
    }

    private boolean collidingWithBarrier() {
        // Quick optimization hack
        List<Tile> barriers = Main.game.world.tileMap.tiles.stream().filter(tile -> tile.getClass().equals(BarrierTile.class) && tile.pos.x < pos.x && tile.pos.x + tile.size.x > pos.x).toList();
        for (Tile barrier : barriers) {
            if(
                    barrier.pos.x < pos.x + size.x &&
                    barrier.pos.x + barrier.size.x > pos.x &&
                    barrier.pos.y < pos.y + size.y &&
                    barrier.pos.y + barrier.size.y > pos.y
            ) {
                return true;
            }
        }
        return false;
    }
}
