package com.alihene.gfx.gui.element.text;

import com.alihene.gfx.*;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.lwjgl.opengl.GL33.*;

public class TextMesh {
    public static final int TEXT_MESH_VERTEX_SIZE = 5;
    public static final int TEXT_MESH_VERTEX_SIZE_BYTES = TEXT_MESH_VERTEX_SIZE * Float.BYTES;
    public static final int TEXT_MESH_MAX_SIZE = 100;

    private final List<GuiText> elements;
    public int elementCount = 0;
    public final float[] data;

    public final int[] indices;

    private Shader shader;
    private final VertexArray vao;
    private final VertexBuffer vbo;

    private final List<CharacterTexture> textures;

    private final RenderSystem renderSystem;

    private final Vector2f[] texCoords;

    public TextMesh(RenderSystem renderSystem) {
        elements = new ArrayList<>();

        data = new float[TEXT_MESH_MAX_SIZE * TEXT_MESH_VERTEX_SIZE * 4];
        indices = new int[TEXT_MESH_MAX_SIZE * 6];

        vao = new VertexArray();
        vbo = new VertexBuffer();

        textures = new ArrayList<>();

        this.renderSystem = renderSystem;

        texCoords = CharacterTexture.getTexCoords();
    }

    public void prepareRender() {
        vao.bind();

        vbo.bind();
        vbo.buffer(data, true);

        IndexBuffer ibo = new IndexBuffer();
        ibo.bind();
        generateIndices();
        ibo.buffer(indices);

        glVertexAttribPointer(0, 2, GL_FLOAT, false, TEXT_MESH_VERTEX_SIZE_BYTES, 0);
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(1, 2, GL_FLOAT, false, TEXT_MESH_VERTEX_SIZE_BYTES, 2 * Float.BYTES);
        glEnableVertexAttribArray(1);
        glVertexAttribPointer(2, 1, GL_FLOAT, false, TEXT_MESH_VERTEX_SIZE_BYTES, 4 * Float.BYTES);
        glEnableVertexAttribArray(2);
    }

    public void meshAt(int index) {
        GuiText element = elements.get(index);

        int offset = index * 5 * 4;

        float xAdd;
        float yAdd;

        float x = element.getPos().x;

        for(char c : element.getString().toCharArray()) {
            GuiCharacter character = element.getCharacters().get(c);

            int texId = 16;

            if (character.getTexture() != null) {
                for (int i = 0; i < textures.size(); i++) {
                    if (textures.get(i) == character.getTexture()) {
                        texId = i % 16;
                    }
                }
            } else {
                System.err.println("Missing texture for GUI element " + element.getClass());
            }

                float xPos = (x + character.getBearing().x * element.getSize()) / 20.0f;
                float yPos = (element.getPos().y - (character.getSize().y - character.getBearing().y) * element.getSize() / 20.0f);

                float w = (character.getSize().x * element.getSize()) / 20.0f;
                float h = (character.getSize().y * element.getSize()) / 20.0f;

                float[] vertices = {
                        xPos, yPos + h, 0.0f, 0.0f, (float) texId,
                        xPos, yPos, 0.0f, 1.0f, (float) texId,
                        xPos + w, yPos, 1.0f, 1.0f, (float) texId,
                        xPos + w, yPos + h, 1.0f, 0.0f, (float) texId
                };

                System.out.println(Arrays.toString(vertices));

                System.arraycopy(vertices, 0, data, (offset), vertices.length);

                x += (((character.getAdvance()) >> 6) * element.getSize());
                offset += 20;
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
        shader.setUniformVec3("uTextColor", new Vector3f(0.0f, 0.0f, 0.0f));
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

            glDrawElements(GL_TRIANGLES, elementCount * 6, GL_UNSIGNED_INT, 0);

            vao.unbind();

            for (int j = i * 16; j < range; j++) {
                textures.get(j).unbind();
            }
        }

        shader.detach();
    }

    public void addElement(GuiText element) {
        for(char c : element.getString().toCharArray()) {
            GuiCharacter character = element.getCharacters().get(c);
            if (character.getTexture() != null) {
                if (!textures.contains(character.getTexture())) {
                    textures.add(character.getTexture());
                }
            } else {
                System.err.println("No texture present for character " + character.getClass());
            }

            elements.add(element);
            elementCount++;
        }
    }

    public void setShader(Shader shader) {
        this.shader = shader;
    }

    public void generateIndices() {
        int offset = 0;
        int indexOffset = 0;

        for (int i = 0; i < TEXT_MESH_MAX_SIZE; i++) {
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
