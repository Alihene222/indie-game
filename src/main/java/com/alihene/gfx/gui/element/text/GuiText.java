package com.alihene.gfx.gui.element.text;

import com.alihene.Main;
import com.alihene.gfx.RenderSystem;
import com.alihene.gfx.gui.GuiMesh;
import com.alihene.gfx.gui.element.GuiImage;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class GuiText {
    private final float size;
    private final Vector2f pos;
    private String string;

    public final List<GuiImage> images;

    private Alignment alignment = Alignment.LEFT;

    public GuiText(Vector2f pos, float size) {
        this.size = size;
        this.pos = pos;
        images = new ArrayList<>();
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public void updateString(String string) {
        if(string == this.string) {
            return;
        }

        float currentAdvances = 0.0f;
        float newAdvances = 0.0f;

        for(char c : this.string.toCharArray()) {
            if(c != 32) {
                currentAdvances += Main.game.renderSystem.textManager.get(c).getAdvance();
            } else {
                currentAdvances += 0.65f;
            }
        }

        for(char c : string.toCharArray()) {
            if(c != 32) {
                newAdvances += Main.game.renderSystem.textManager.get(c).getAdvance();
            } else {
                newAdvances += 0.65f;
            }
        }

        this.string = string;

        if(newAdvances != currentAdvances) {
            List<GuiMesh> meshes = new ArrayList<>();

            for (GuiImage image : images) {
                if (!meshes.contains(image.mesh)) {
                    meshes.add(image.mesh);
                }
                Main.game.renderSystem.guiCollection.removeElement(image);
            }

            images.clear();

            updateImages(Main.game.renderSystem);

            for (GuiImage image : images) {
                Main.game.renderSystem.guiCollection.addElementToMesh(image);
            }

            for (GuiMesh mesh : meshes) {
                mesh.mesh();
            }
        } else {
            int i = 0;
            for(char c : string.toCharArray()) {
                if(c != 32) {
                    GuiCharacter character = Main.game.renderSystem.textManager.get(c);
                    images.get(i).texturePos = character.getTexturePos();
                    images.get(i).updateMesh();
                    i++;
                }
            }
        }
    }

    public float getSize() {
        return size;
    }

    public Vector2f getPos() {
        return pos;
    }

    public Alignment getAlignment() {
        return alignment;
    }

    public void setAlignment(Alignment alignment) {
        this.alignment = alignment;
    }

    public void updateImages(RenderSystem renderSystem) {
        images.clear();

        float x = pos.x;
        if(alignment == Alignment.LEFT) {
            for (char c : string.toCharArray()) {
                if (c != 32) {
                    GuiCharacter character = renderSystem.textManager.get(c);
                    images.add(character.image(renderSystem, new Vector2f(x, pos.y), size));
                    x += character.getAdvance() * size;
                } else {
                    x += 0.65f * size;
                }
            }
        } else {
            float advances = 0.0f;

            for(char c : string.toCharArray()) {
                if (c != 32) {
                    advances += renderSystem.textManager.get(c).getAdvance();
                } else {
                    advances += 0.65f;
                }
            }

            x = pos.x - (advances * size);

            for(char c : string.toCharArray()) {
                if (c != 32) {
                    GuiCharacter character = renderSystem.textManager.get(c);
                    images.add(character.image(renderSystem, new Vector2f(x, pos.y), size));
                    x += character.getAdvance() * size;
                } else {
                    x += 0.65f * size;
                }
            }
        }
    }

    public List<GuiImage> getImages() {
        return images;
    }

    public enum Alignment {
        LEFT,
        RIGHT
    }
}