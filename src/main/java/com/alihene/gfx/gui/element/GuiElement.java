package com.alihene.gfx.gui.element;

import com.alihene.gfx.gui.GuiMesh;
import com.alihene.world.gameobject.Gameobject;
import org.joml.Vector2f;
import org.joml.Vector2i;

public abstract class GuiElement extends Gameobject {
    public Vector2i texturePos = new Vector2i(0, 0);
    public Vector2i textureSize = new Vector2i(16, 16);

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
