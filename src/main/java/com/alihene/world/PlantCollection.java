package com.alihene.world;

import com.alihene.Main;
import com.alihene.gfx.RenderSystem;
import com.alihene.world.gameobject.plant.Plant;
import com.alihene.world.gfx.PlantMesh;

import java.util.ArrayList;
import java.util.List;

public class PlantCollection implements Tickable {
    public final List<Plant> plants;
    private final List<PlantMesh> meshes;

    public PlantCollection() {
        plants = new ArrayList<>();
        meshes = new ArrayList<>();
    }

    public void render() {
        for(PlantMesh mesh : meshes) {
            mesh.render();
        }
    }

    public void addPlant(Plant plant) {
        plants.add(plant);
        addPlantToMesh(plant);
    }

    public void addPlantToMesh(Plant plant) {
        boolean added = false;

        for(PlantMesh mesh : meshes) {
            if(mesh.plantCount < PlantMesh.PLANT_MESH_MAX_SIZE) {
                mesh.addPlant(plant);
                plant.plantCollection = this;
                plant.mesh = mesh;
                plant.index = mesh.plantCount - 1;
                mesh.meshAt(plant.index);
                added = true;
                break;
            }
        }

        if(!added) {
            PlantMesh newMesh = new PlantMesh();
            newMesh.setShader(Main.game.renderSystem.getShader(RenderSystem.PLANT_SHADER));
            newMesh.addPlant(plant);
            plant.plantCollection = this;
            plant.mesh = newMesh;
            plant.index = 0;
            newMesh.meshAt(plant.index);
            newMesh.prepareRender();
            meshes.add(newMesh);
        }
    }

    @Override
    public void tick() {
        for(Plant plant : plants) {
            plant.tick();
        }
    }
}
