package com.alihene.gfx.gui.element;

import com.alihene.Main;
import com.alihene.gfx.gui.GuiMesh;
import com.alihene.world.gameobject.Gameobject;
import org.joml.Vector2f;

public abstract class GuiElement extends Gameobject {
    public GuiMesh mesh;
    public int index = -1;

    private float offsetX = 0; // Temporary value
    private float offsetY = 0; // Temporary value

    private int dockX, dockY = -1;

    public static final int DOCK_X_LEFT = 0;
    public static final int DOCK_X_RIGHT = 1;
    public static final int DOCK_Y_TOP = 2;
    public static final int DOCK_Y_BOTTOM = 3;

    public GuiElement(Vector2f pos, Vector2f size) {
        super(pos, size);
    }

    @Override
    public void updateMesh() {
        mesh.meshAt(index);
    }

    @Override
    public void tick(float delta) {
//        if(dockX < 0 || dockY < 0) {
//            throw new IllegalStateException("Docks not set for GUI element " + getClass());
//        }
//
//        if(dockX == DOCK_X_LEFT) {
//            pos.x = offsetX;
//        } else if (dockX == DOCK_X_RIGHT) {
//            pos.x = Main.game.window.getDimensions().x - offsetX;
//        } else {
//            throw new IllegalStateException("Invalid X dock value for GUI element " + getClass() + ": " + dockX);
//        }
//
//        if(dockY == DOCK_Y_TOP) {
//            pos.y = Main.game.window.getDimensions().y - offsetY;
//        } else if (dockY == DOCK_Y_BOTTOM) {
//            pos.y = offsetY;
//        } else {
//            throw new IllegalStateException("Invalid Y dock value for GUI element " + getClass() + ": " + dockY);
//        }
    }

    public int getDockX() {
        return dockX;
    }

    public void setDockX(int dockX) {
        this.dockX = dockX;
    }

    public int getDockY() {
        return dockY;
    }

    public void setDockY(int dockY) {
        this.dockY = dockY;
    }
}
