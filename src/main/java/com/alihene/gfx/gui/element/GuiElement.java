package com.alihene.gfx.gui.element;

import com.alihene.Main;
import com.alihene.gfx.gui.GuiMesh;
import com.alihene.world.gameobject.Gameobject;
import org.joml.Vector2f;

public abstract class GuiElement extends Gameobject {
    public GuiMesh mesh;
    public int index = -1;

    public GuiElement(Vector2f pos, Vector2f size) {
        super(pos, size);
    }

    @Override
    public void updateMesh() {
        mesh.meshAt(index);
    }

    @Override
    public void tick(float delta) {

    }
}
