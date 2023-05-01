package com.alihene.gfx.util;

import com.alihene.Main;
import com.alihene.gfx.Window;
import com.alihene.world.Tickable;
import com.alihene.world.gameobject.player.Player;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Camera implements Tickable {
    public Vector2f position;
    private final Matrix4f view, projection;

    public Camera() {
        position = new Vector2f(0.0f, 0.0f);
        view = new Matrix4f();
        projection = new Matrix4f();
    }

    public void adjustProjection() {
        Window window = Main.game.window;
        projection.identity();
        if (window.getAspectRatio() >= 1.0) {
            projection.ortho(0.0f, 20.0f, 0.0f, 20.0f, 0.0f, 100.0f);
        } else {
            projection.ortho(0.0f, 20.0f, 0.0f, 20.0f, 0.0f, 100.0f);
        }
    }

    public Matrix4f getProjectionMatrix() {
        return projection;
    }

    public Matrix4f getViewMatrix() {
        return getViewMatrix(position);
    }

    public Matrix4f getViewMatrix(Vector2f position) {
        view.identity().lookAt(
                new Vector3f(position.x, position.y, 0.0f),
                new Vector3f(position.x, position.y, 0.0f)
                        .add(new Vector3f(0.0f, 0.0f, -1.0f)),
                new Vector3f(0.0f, 1.0f, 0.0f));
        return view;
    }

    @Override
    public void tick() {
        Player player = Main.game.world.player;
        position.x = player.pos.x - 10.0f + player.size.x / 2.0f;
        position.y = player.pos.y - 10.0f + player.size.y / 2.0f;
    }
}
