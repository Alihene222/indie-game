package com.alihene.world;

import com.alihene.Main;
import com.alihene.gfx.RenderSystem;
import com.alihene.world.gameobject.Entity;
import com.alihene.world.gfx.EntityMesh;

import java.util.ArrayList;
import java.util.List;

public class EntityCollection implements Tickable {
    private final List<Entity> entities;
    public final List<EntityMesh> meshes;

    public EntityCollection() {
        entities = new ArrayList<>();
        meshes = new ArrayList<>();
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
        addEntityToMesh(entity);
    }

    private void addEntityToMesh(Entity entity) {
        boolean added = false;

        for(EntityMesh mesh : meshes) {
            if(mesh.entityCount < EntityMesh.ENTITY_MESH_MAX_SIZE) {
                if(mesh.textures.size() < 16 || mesh.textures.contains(entity.getTexture())) {
                    mesh.addEntity(entity);
                    entity.mesh = mesh;
                    entity.index = mesh.entityCount - 1;
                    mesh.meshAt(entity.index);
                    added = true;
                    break;
                }
            }
        }

        if(!added) {
            EntityMesh newMesh = new EntityMesh();
            newMesh.setShader(Main.game.renderSystem.getShader(RenderSystem.ENTITY_SHADER));
            newMesh.addEntity(entity);
            entity.mesh = newMesh;
            entity.index = 0;
            newMesh.meshAt(entity.index);
            newMesh.prepareRender();
            meshes.add(newMesh);
        }
    }

    public void render() {
        for(EntityMesh mesh : meshes) {
            mesh.render();
        }
    }

    @Override
    public void tick() {
        for (Entity entity : entities) {
            entity.tick();
        }
    }
}
