package com.alihene.world.gfx;

import com.alihene.Main;
import com.alihene.gfx.*;
import com.alihene.world.gameobject.Entity;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL33.*;

public class EntityMesh {
    public static final int ENTITY_MESH_VERTEX_SIZE = 6;
    public static final int ENTITY_MESH_VERTEX_SIZE_BYTES = ENTITY_MESH_VERTEX_SIZE * Float.BYTES;
    public static final int ENTITY_MESH_MAX_SIZE = 100;

    public final List<Entity> entities;
    public int entityCount;
    public final float[] data;
    public final int[] indices;

    private Shader shader;
    private final VertexArray vao;
    private final VertexBuffer vbo;
    private boolean shouldBuffer = false;

    public final List<Texture> textures;

    private final RenderSystem renderSystem;

    public EntityMesh() {
        entities = new ArrayList<>();
        entityCount = 0;
        data = new float[ENTITY_MESH_MAX_SIZE * ENTITY_MESH_VERTEX_SIZE * 4];
        indices = new int[ENTITY_MESH_MAX_SIZE * 6];

        vao = new VertexArray();
        vbo = new VertexBuffer();

        textures = new ArrayList<>();

        renderSystem = Main.game.renderSystem;
    }

    public void prepareRender() {
        vao.bind();

        vbo.bind();
        vbo.buffer(ENTITY_MESH_MAX_SIZE * ENTITY_MESH_VERTEX_SIZE * 4, true);

        IndexBuffer ibo = new IndexBuffer();
        ibo.bind();
        generateIndices();
        ibo.buffer(indices);

        glVertexAttribPointer(0, 3, GL_FLOAT, false, ENTITY_MESH_VERTEX_SIZE_BYTES, 0);
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(1, 2, GL_FLOAT, false, ENTITY_MESH_VERTEX_SIZE_BYTES, 3 * Float.BYTES);
        glEnableVertexAttribArray(1);
        glVertexAttribPointer(2, 1, GL_FLOAT, false, ENTITY_MESH_VERTEX_SIZE_BYTES, 5 * Float.BYTES);
        glEnableVertexAttribArray(2);
    }

    public void meshAt(int index) {
        Entity entity = entities.get(index);

        if(!shouldBuffer) {
            shouldBuffer = true;
        }

        int texId = 16;

        int offset = index * 6 * 4;

        if(entity.getTexture() != null) {
            for(int i = 0; i < textures.size(); i++) {
                if(textures.get(i) == entity.getTexture()) {
                    texId = i % 16;
                }
            }
            if(texId == 16) {
                textures.add(entity.getTexture());
                texId = textures.indexOf(entity.getTexture()) % 16;
            }
        } else {
            System.err.println("Missing texture for entity " + entity.getClass());
        }

        float xAdd;
        float yAdd;

        for(int i = 0; i < 4; i++) { // 4 Vertices per quad
            if(i == 0) {
                xAdd = 0.0f;
                yAdd = 0.0f;
            } else if(i == 1) {
                xAdd = 1.0f;
                yAdd = 0.0f;
            } else if (i == 2) {
                xAdd = 1.0f;
                yAdd = 1.0f;
            } else {
                xAdd = 0.0f;
                yAdd = 1.0f;
            }

            data[offset] = entity.pos.x + (xAdd * entity.size.x);
            data[offset + 1] = entity.pos.y + (yAdd * entity.size.y);
            data[offset + 2] = 0.0f;

            data[offset + 3] = xAdd;
            data[offset + 4] = yAdd;

            data[offset + 5] = texId;

            offset += ENTITY_MESH_VERTEX_SIZE;
        }
    }

    public void render() {
        if(shouldBuffer) {
            vbo.bind();
            vbo.bufferSub(data);
            shouldBuffer = false;
        }

        int iterations;
        iterations = (int) Math.ceil((float) textures.size() / 16.0f);
        if(iterations == 0) {
            iterations = 1;
        }

        shader.use();
        shader.setUniformIntArray("uTextures", RenderSystem.TEX_SLOTS);
        shader.setUniformMat4("uView", renderSystem.getCamera().getViewMatrix());
        shader.setUniformMat4("uProjection", renderSystem.getCamera().getProjectionMatrix());

        for(int i = 0; i < iterations; i++) {
            int range = (i + 1 == iterations) ? textures.size() : (i * 16) + 16;
            for(int j = i * 16; j < range; j++) {
                //If j is greater than 15, modulo of j. If less than, then just j.
                int activateIndex = j > 15 ? j % 16 : j;

                textures.get(j).activate(activateIndex);
                textures.get(j).bind();
            }
            vao.bind();
            glEnableVertexAttribArray(0);
            glEnableVertexAttribArray(1);
            glEnableVertexAttribArray(2);

            glDrawElements(GL_TRIANGLES, entityCount * 6, GL_UNSIGNED_INT, 0);

            glDisableVertexAttribArray(0);
            glDisableVertexAttribArray(1);
            glDisableVertexAttribArray(2);

            vao.unbind();

            for(int j = i * 16; j < range; j++) {
                textures.get(j).unbind();
            }
        }

        shader.detach();
    }

    public void addEntity(Entity entity) {
        if(entity.getTexture() != null) {
            if(!textures.contains(entity.getTexture())) {
                textures.add(entity.getTexture());
            }
        } else {
            System.err.println("No texture present for entity " + entity.getClass());
        }

        entities.add(entity);
        entityCount++;
    }

    public void setShader(Shader shader) {
        this.shader = shader;
    }

    public void generateIndices() {
        int offset = 0;
        int indexOffset = 0;

        for(int i = 0; i < ENTITY_MESH_MAX_SIZE; i++) {
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