package com.alihene.gfx.gui;

import com.alihene.gfx.*;
import com.alihene.gfx.gui.element.GuiElement;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL33.*;

public class GuiMesh {
    public static final int GUI_MESH_VERTEX_SIZE = 6;
    public static final int GUI_MESH_VERTEX_SIZE_BYTES = GUI_MESH_VERTEX_SIZE * Float.BYTES;
    public static final int GUI_MESH_MAX_SIZE = 100;

    public final List<GuiElement> elements;
    public int elementCount;
    public final float[] data;
    public final int[] indices;

    private Shader shader;
    private final VertexArray vao;
    private final VertexBuffer vbo;

    private final List<Texture> textures;

    private final RenderSystem renderSystem;

    public GuiMesh(RenderSystem renderSystem) {
        elements = new ArrayList<>();

        data = new float[GUI_MESH_MAX_SIZE * GUI_MESH_VERTEX_SIZE * 4];
        indices = new int[GUI_MESH_MAX_SIZE * 6];

        vao = new VertexArray();
        vbo = new VertexBuffer();

        textures = new ArrayList<>();

        this.renderSystem = renderSystem;
    }

    public void prepareRender() {
        vao.bind();

        vbo.bind();
        vbo.buffer(data, true);

        IndexBuffer ibo = new IndexBuffer();
        ibo.bind();
        generateIndices();
        ibo.buffer(indices);

        glVertexAttribPointer(0, 3, GL_FLOAT, false, GUI_MESH_VERTEX_SIZE_BYTES, 0);
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(1, 2, GL_FLOAT, false, GUI_MESH_VERTEX_SIZE_BYTES, 3 * Float.BYTES);
        glEnableVertexAttribArray(1);
        glVertexAttribPointer(2, 1, GL_FLOAT, false, GUI_MESH_VERTEX_SIZE_BYTES, 5 * Float.BYTES);
        glEnableVertexAttribArray(2);
    }

    public void meshAt(int index) {
        GuiElement element = elements.get(index);

        int texId = 16;

        int offset = index * 6 * 4;

        if (element.getTexture() != null) {
            for (int i = 0; i < textures.size(); i++) {
                if (textures.get(i) == element.getTexture()) {
                    texId = i % 16;
                }
            }
        } else {
            System.err.println("Missing texture for GUI element " + element.getClass());
        }

        float xAdd;
        float yAdd;

        for (int i = 0; i < 4; i++) { // 4 Vertices per quad
            if (i == 0) {
                xAdd = 0.0f;
                yAdd = 0.0f;
            } else if (i == 1) {
                xAdd = 1.0f;
                yAdd = 0.0f;
            } else if (i == 2) {
                xAdd = 1.0f;
                yAdd = 1.0f;
            } else {
                xAdd = 0.0f;
                yAdd = 1.0f;
            }

            data[offset] = element.pos.x + (xAdd * element.size.x);
            data[offset + 1] = element.pos.y + (yAdd * element.size.y);
            data[offset + 2] = 0.0f;

            data[offset + 3] = xAdd;
            data[offset + 4] = yAdd;

            data[offset + 5] = texId;

            offset += GUI_MESH_VERTEX_SIZE;
        }
    }

    public void render() {
        vbo.bind();
        vbo.buffer(data, true);

        int iterations;
        iterations = (int) Math.ceil((float) textures.size() / 16.0f);
        if (iterations == 0) {
            iterations = 1;
        }

        shader.use();
        shader.setUniformIntArray("uTextures", RenderSystem.TEX_SLOTS);
        shader.setUniformMat4(
                "uView",
                renderSystem.getCamera().getViewMatrix(new Vector2f(0.0f, 0.0f)));
        shader.setUniformMat4("uProjection", renderSystem.getCamera().getProjectionMatrix());

        for (int i = 0; i < iterations; i++) {
            int range = (i + 1 == iterations) ? textures.size() : (i * 16) + 16;
            for (int j = i * 16; j < range; j++) {
                //If j is greater than 15, modulo of j. If less than, then just j.
                int activateIndex = j > 15 ? j % 16 : j;

                textures.get(j).activate(activateIndex);
                textures.get(j).bind();
            }
            vao.bind();
            glEnableVertexAttribArray(0);
            glEnableVertexAttribArray(1);
            glEnableVertexAttribArray(2);

            glDrawElements(GL_TRIANGLES, elementCount * 6, GL_UNSIGNED_INT, 0);

            glDisableVertexAttribArray(0);
            glDisableVertexAttribArray(1);
            glDisableVertexAttribArray(2);

            vao.unbind();

            for (int j = i * 16; j < range; j++) {
                textures.get(j).unbind();
            }
        }

        shader.detach();
    }

    public void addElement(GuiElement element) {
        if (element.getTexture() != null) {
            if (!textures.contains(element.getTexture())) {
                textures.add(element.getTexture());
            }
        } else {
            System.err.println("No texture present for element " + element.getClass());
        }

        elements.add(element);
        elementCount++;
    }

    public void setShader(Shader shader) {
        this.shader = shader;
    }

    public void generateIndices() {
        int offset = 0;
        int indexOffset = 0;

        for (int i = 0; i < GUI_MESH_MAX_SIZE; i++) {
            indices[offset] = indexOffset;
            indices[offset + 1] = indexOffset + 1;
            indices[offset + 2] = indexOffset + 2;

            indices[offset + 3] = indexOffset + 2;
            indices[offset + 4] = indexOffset + 3;
            indices[offset + 5] = indexOffset;

            offset += 6;
            indexOffset += 4;
        }
    }
}