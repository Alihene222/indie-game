package com.alihene.gfx.gui;

import com.alihene.Main;
import com.alihene.gfx.RenderSystem;
import com.alihene.gfx.gui.element.GuiElement;
import com.alihene.gfx.gui.element.GuiImage;
import com.alihene.gfx.gui.element.GuiSelector;
import com.alihene.gfx.gui.element.text.GuiCharacter;
import com.alihene.gfx.gui.element.text.GuiText;
import com.alihene.world.Tickable;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class GuiCollection implements Tickable {
    private final List<GuiElement> elements;
    private final List<GuiText> textElements;
    private final List<GuiMesh> meshes;

    public GuiSelector selector;

    private final RenderSystem renderSystem;

    public GuiCollection(RenderSystem renderSystem) {
        elements = new ArrayList<>();
        textElements = new ArrayList<>();
        meshes = new ArrayList<>();

        this.renderSystem = renderSystem;
    }

    public void init() {
        selector = new GuiSelector(new Vector2f(0.0f, 0.0f), new Vector2f(2.0f, 2.0f));
        selector.setTexture(renderSystem.getTextureByName("selector"));
        addElement(selector);

        for(float i = 5.0f; i < 15.0f; i += 2.0f) {
            GuiImage testImage = new GuiImage(new Vector2f(i, 0.0f), new Vector2f(2.0f, 2.0f));
            testImage.setTexture(renderSystem.getTextureByName("hotbar_slot"));
            addElement(testImage);
        }

        for(int i = 0; i < 5; i++) {
            GuiImage heart = new GuiImage(new Vector2f((i * 1.1f) + 0.15f, 20.0f - 1.15f), new Vector2f(1.0f, 1.0f));
            heart.setTexture(renderSystem.getTextureByName("heart"));
            addElement(heart);
        }

        GuiText text = new GuiText(new Vector2f(19.75f, 19.25f), 0.5f);
        text.setAlignment(GuiText.Alignment.RIGHT);
        text.setString("FPS: 60");
        addText(text);
    }

    public void addElement(GuiElement element) {
        elements.add(element);
        addElementToMesh(element);
    }

    public void addText(GuiText text) {
        textElements.add(text);
        addTextToMesh(text);
    }

    public void removeElement(GuiElement element) {
        element.mesh.removeElement(element);
        elements.remove(element);
    }

    public void addTextToMesh(GuiText text) {
        float x = text.getPos().x;
        if(text.getAlignment() == GuiText.Alignment.LEFT) {
            for (char c : text.getString().toCharArray()) {
                if (c != 32) {
                    GuiCharacter character = renderSystem.textManager.get(c);
                    addElementToMesh(character.image(renderSystem, new Vector2f(x, text.getPos().y), text.getSize()));
                    x += character.getAdvance() * text.getSize();
                } else {
                    x += 0.65f * text.getSize();
                }
            }
        } else {
            float advances = 0.0f;

            for(char c : text.getString().toCharArray()) {
                if (c != 32) {
                    advances += renderSystem.textManager.get(c).getAdvance();
                } else {
                    advances += 0.65f;
                }
            }

            x = text.getPos().x - (advances * text.getSize());

            for(char c : text.getString().toCharArray()) {
                if (c != 32) {
                    GuiCharacter character = renderSystem.textManager.get(c);
                    addElementToMesh(character.image(renderSystem, new Vector2f(x, text.getPos().y), text.getSize()));
                    x += character.getAdvance() * text.getSize();
                } else {
                    x += 0.65f * text.getSize();
                }
            }
        }
    }

    private void addElementToMesh(GuiElement element) {
        boolean added = false;

        for (GuiMesh mesh : meshes) {
            if (mesh.elementCount < GuiMesh.GUI_MESH_MAX_SIZE) {
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
