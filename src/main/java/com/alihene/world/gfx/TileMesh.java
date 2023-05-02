package com.alihene.world.gfx;

import com.alihene.Main;
import com.alihene.gfx.*;
import com.alihene.world.gameobject.tile.Tile;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL33.*;

public class TileMesh {
    public static final int TILE_MESH_VERTEX_SIZE = 6;
    public static final int TILE_MESH_VERTEX_SIZE_BYTES = TILE_MESH_VERTEX_SIZE * Float.BYTES;
    public static final int TILE_MESH_MAX_SIZE = 10000;

    public final List<Tile> tiles;
    public int tileCount;
    public final float[] data;
    public int[] indices;

    private Shader shader;
    private final VertexArray vao;
    private final VertexBuffer vbo;

    private final List<Texture> textures;

    private boolean shouldBuffer = false;

    private final RenderSystem renderSystem;

    public TileMesh() {
        tiles = new ArrayList<>();
        tileCount = 0;
        data = new float[TILE_MESH_MAX_SIZE * TILE_MESH_VERTEX_SIZE * 4];
        indices = new int[TILE_MESH_MAX_SIZE * 6];

        vao = new VertexArray();
        vbo = new VertexBuffer();

        textures = new ArrayList<>();

        renderSystem = Main.game.renderSystem;
    }

    public void prepareRender() {
        vao.bind();

        vbo.bind();
        vbo.buffer(TILE_MESH_MAX_SIZE * TILE_MESH_VERTEX_SIZE * 4, false);

        IndexBuffer ibo = new IndexBuffer();
        ibo.bind();
        generateIndices();
        ibo.buffer(indices);

        vao.attrib(0, 3, GL_FLOAT, TILE_MESH_VERTEX_SIZE_BYTES, 0);
        vao.attrib(1, 2, GL_FLOAT, TILE_MESH_VERTEX_SIZE_BYTES, 3 * Float.BYTES);
        vao.attrib(2, 1, GL_FLOAT, TILE_MESH_VERTEX_SIZE_BYTES, 5 * Float.BYTES);
    }

    public void meshAt(int index) {
        if(!shouldBuffer) {
            shouldBuffer = true;
        }

        Tile tile = tiles.get(index);

        int texId = 16;

        int offset = index * 6 * 4;

        if(tile.getTexture() != null) {
            for(int i = 0; i < textures.size(); i++) {
                if(textures.get(i) == tile.getTexture()) {
                    if(i > 15) {
                        texId = 0;
                    } else {
                        texId = i;
                    }
                }
            }
        } else {
            System.err.println("Missing texture for tile" + tile.getClass());
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

            data[offset] = tile.pos.x + (xAdd * tile.size.x);
            data[offset + 1] = tile.pos.y + (yAdd * tile.size.y);
            data[offset + 2] = 0.0f;

            data[offset + 3] = xAdd;
            data[offset + 4] = yAdd;

            data[offset + 5] = texId;

            offset += TILE_MESH_VERTEX_SIZE;
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

            glDrawElements(GL_TRIANGLES, tileCount * 6, GL_UNSIGNED_INT, 0);

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

    public void addTile(Tile tile) {
        if(tile.getTexture() != null) {
            if(!textures.contains(tile.getTexture())) {
                textures.add(tile.getTexture());
            }
        } else {
            System.err.println("No texture present for tile " + tile.getClass());
        }

        tiles.add(tile);
        tileCount++;
    }

    public void setShader(Shader shader) {
        this.shader = shader;
    }

    private void generateIndices() {
        int offset = 0;
        int indexOffset = 0;

        for(int i = 0; i < TILE_MESH_MAX_SIZE; i++) {
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