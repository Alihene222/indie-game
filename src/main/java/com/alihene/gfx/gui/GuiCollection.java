package com.alihene.gfx.gui;

import com.alihene.Main;
import com.alihene.gfx.RenderSystem;
import com.alihene.gfx.gui.element.GuiElement;
import com.alihene.world.Tickable;

import java.util.ArrayList;
import java.util.List;

public class GuiCollection implements Tickable {
    private final List<GuiElement> elements;
    private final List<GuiMesh> meshes;

    private final RenderSystem renderSystem;

    public GuiCollection(RenderSystem renderSystem) {
        elements = new ArrayList<>();
        meshes = new ArrayList<>();

        this.renderSystem = renderSystem;
    }

    public void addElement(GuiElement element) {
        elements.add(element);
        addElementToMesh(element);
    }

    private void addElementToMesh(GuiElement element) {
        boolean added = false;

        for(GuiMesh mesh : meshes) {
            if(mesh.elementCount < GuiMesh.GUI_MESH_MAX_SIZE) {
                mesh.addElement(element);
                element.mesh = mesh;
                element.index = mesh.elementCount - 1;
                mesh.meshAt(element.index);
                added = true;
                break;
            }
        }

        if(!added) {
            GuiMesh newMesh = new GuiMesh(renderSystem);
            newMesh.setShader(Main.game.renderSystem.getShader(RenderSystem.GUI_SHADER));
            newMesh.addElement(element);
            element.mesh = newMesh;
            element.index = 0;
            newMesh.meshAt(0);
            newMesh.prepareRender();
            meshes.add(newMesh);
        }
    }

    public void render() {
        for(GuiMesh mesh : meshes) {
            mesh.render();
        }
    }

    @Override
    public void tick(float delta) {
        for(GuiElement element : elements) {
            element.tick(delta);
        }
    }
}
