package com.alihene.world.gfx;

import com.alihene.Main;
import com.alihene.gfx.*;
import com.alihene.world.gameobject.plant.Plant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.lwjgl.opengl.GL33.*;

public class PlantMesh {
    public static final int PLANT_MESH_VERTEX_SIZE = 6;
    public static final int PLANT_MESH_VERTEX_SIZE_BYTES = PLANT_MESH_VERTEX_SIZE * Float.BYTES;
    public static final int PLANT_MESH_MAX_SIZE = 100;

    public final List<Plant> plants;
    public int plantCount;
    public float[] data;
    public final int[] indices;

    private Shader shader;
    private final VertexArray vao;
    private final VertexBuffer vbo;
    private boolean shouldBuffer = false;

    public final List<Texture> textures;

    private final RenderSystem renderSystem;

    public PlantMesh() {
        plants = new ArrayList<>();
        plantCount = 0;
        data = new float[PLANT_MESH_MAX_SIZE * PLANT_MESH_VERTEX_SIZE * 4];
        indices = new int[PLANT_MESH_MAX_SIZE * 6];

        vao = new VertexArray();
        vbo = new VertexBuffer();

        textures = new ArrayList<>();

        renderSystem = Main.game.renderSystem;
    }

    public void prepareRender() {
        vao.bind();

        vbo.bind();
        vbo.buffer(PLANT_MESH_MAX_SIZE * PLANT_MESH_VERTEX_SIZE * 4, true);

        IndexBuffer ibo = new IndexBuffer();
        ibo.bind();
        generateIndices();
        ibo.buffer(indices);

        vao.attrib(0, 3, GL_FLOAT, PLANT_MESH_VERTEX_SIZE_BYTES, 0);
        vao.attrib(1, 2, GL_FLOAT, PLANT_MESH_VERTEX_SIZE_BYTES, 3 * Float.BYTES);
        vao.attrib(2, 1, GL_FLOAT, PLANT_MESH_VERTEX_SIZE_BYTES, 5 * Float.BYTES);
    }

    public void mesh() {
        data = new float[PLANT_MESH_MAX_SIZE * PLANT_MESH_VERTEX_SIZE * 4];
        for(int i = 0; i < plants.size(); i++) {
            meshAt(i);
        }
    }

    public void meshAt(int index) {
        Plant plant = plants.get(index);

        if(!shouldBuffer) {
            shouldBuffer = true;
        }

        int texId = 16;

        int offset = index * 6 * 4;

        if(plant.getTexture() != null) {
            for(int i = 0; i < textures.size(); i++) {
                if(textures.get(i) == plant.getTexture()) {
                    if(i > 15) {
                        texId = 0;
                    } else {
                        texId = i;
                    }
                }
            }
        } else {
            System.err.println("Missing texture for plant" + plant.getClass());
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

            data[offset] = plant.pos.x + (xAdd * plant.size.x);
            data[offset + 1] = plant.pos.y + (yAdd * plant.size.y);
            data[offset + 2] = 0.0f;

            data[offset + 3] = ((xAdd / plant.getTextureInfo().totalSize.x) * plant.getTextureInfo().size.x) + ((float) plant.getTextureInfo().pos.x / plant.getTextureInfo().totalSize.x);
            data[offset + 4] = ((yAdd / plant.getTextureInfo().totalSize.y) * plant.getTextureInfo().size.y) + ((float) plant.getTextureInfo().pos.y / plant.getTextureInfo().totalSize.y);

            data[offset + 5] = texId;

            offset += PLANT_MESH_VERTEX_SIZE;
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

            glDrawElements(GL_TRIANGLES, plantCount * 6, GL_UNSIGNED_INT, 0);

            vao.unbind();

            for(int j = i * 16; j < range; j++) {
                textures.get(j).unbind();
            }
        }

        shader.detach();
    }

    public void addPlant(Plant plant) {
        if(plant.getTexture() != null) {
            if(!textures.contains(plant.getTexture())) {
                textures.add(plant.getTexture());
            }
        } else {
            System.err.println("No texture present for plant" + plant.getClass());
        }

        plants.add(plant);
        plantCount++;
    }

    public void setShader(Shader shader) {
        this.shader = shader;
    }

    private void generateIndices() {
        int offset = 0;
        int indexOffset = 0;

        for(int i = 0; i < PLANT_MESH_MAX_SIZE; i++) {
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
