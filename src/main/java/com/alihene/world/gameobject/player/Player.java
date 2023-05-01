package com.alihene.world.gameobject.player;

import com.alihene.Main;
import com.alihene.gfx.Window;
import com.alihene.world.gameobject.Entity;
import com.alihene.world.gameobject.player.item.Hotbar;
import com.alihene.world.gameobject.player.item.Inventory;
import com.alihene.world.gameobject.tile.BarrierTile;
import com.alihene.world.gameobject.tile.Tile;
import org.joml.Vector2f;

import java.util.List;

import static org.lwjgl.glfw.GLFW.*;

public class Player extends Entity {
    private final Inventory inventory;
    private final Hotbar hotbar;

    public Player(Vector2f pos, Vector2f size) {
        super(pos, size);
        setHealth(10);

        inventory = new Inventory();
        hotbar = new Hotbar();
    }

    @Override
    public void tick() {
        if(mesh != null && index > -1) {
            boolean anyPressed = false;

            float speed = 0.2f;

            Window window = Main.game.window;

            if(window.keyPressed(GLFW_KEY_SPACE)) {
                speed *= 2.0f;
            }

            if (window.keyPressed(GLFW_KEY_W)) {
                anyPressed = true;
                pos.y += speed;
                if(collidingWithBarrier()) {
                    while(collidingWithBarrier()) {
                        pos.y -= 0.01f;
                    }
                }
            }
            if (window.keyPressed(GLFW_KEY_S)) {
                anyPressed = true;
                pos.y -= speed;
                if(collidingWithBarrier()) {
                    while(collidingWithBarrier()) {
                        pos.y += 0.01f;
                    }
                }
            }
            if (window.keyPressed(GLFW_KEY_A)) {
                anyPressed = true;
                pos.x -= speed;
                if(collidingWithBarrier()) {
                    while(collidingWithBarrier()) {
                        pos.x += 0.01f;
                    }
                }
            }
            if (window.keyPressed(GLFW_KEY_D)) {
                anyPressed = true;
                pos.x += speed;
                if(collidingWithBarrier()) {
                    while(collidingWithBarrier()) {
                        pos.x -= 0.01f;
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

        hotbar.tick();
        inventory.tick();
    }

    private boolean collidingWithBarrier() {
        // Quick optimization hack
        List<Tile> barriers = Main.game.world.tileMap.tiles.stream().filter(tile -> tile.getClass().equals(BarrierTile.class)).toList();
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

    public Hotbar getHotbar() {
        return hotbar;
    }

    public Inventory getInventory() {
        return inventory;
    }
}
