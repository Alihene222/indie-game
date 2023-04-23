package com.alihene.world.gameobject.player.item;

import com.alihene.Main;
import com.alihene.gfx.RenderSystem;
import com.alihene.gfx.gui.GuiMesh;
import com.alihene.gfx.gui.element.GuiImage;
import com.alihene.world.Tickable;
import org.joml.Vector2f;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class Hotbar implements Tickable {
    private static final float POSITION_X = 5.0f;

    private final List<ItemStack> stacks;

    private final GuiImage[] images;

    private volatile boolean updated = false;

    public Hotbar()  {
        stacks = new ArrayList<>();
        images = new GuiImage[5];
    }

    public ItemStack getItemStack(int index) {
        return stacks.get(index);
    }

    public boolean hasItem(Class<? extends Item> item) {
        for(ItemStack stack : stacks) {
            if(stack.getItemClass().equals(item)) {
                return stack.size() > 0;
            }
        }
        return false;
    }

    public ItemStack getStack(Class<? extends Item> item) {
        for(ItemStack stack : stacks) {
            if(stack.getItemClass().equals(item)) {
                return stack;
            }
        }
        return null;
    }

    public void addItem(Class<? extends Item> item) {
        addItem(item, 1);
    }

    public void addItem(Class<? extends Item> item, int amount) {
        boolean added = false;

        for(ItemStack stack : stacks) {
            if(stack.getItemClass().equals(item)) {
                try {
                    for(int i = 0; i < amount; i++) {
                        stack.addItem(item.getConstructor(ItemStack.class).newInstance(stack));
                    }
                    added = true;
                } catch(NoSuchMethodException | InstantiationException | IllegalAccessException |
                        InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        if(!added) {
            if(stacks.size() < 5) {
                ItemStack stack = new ItemStack(item);
                try {
                    for(int i = 0; i < amount; i++) {
                        stack.addItem(item.getConstructor(ItemStack.class).newInstance(stack));
                    }
                } catch (NoSuchMethodException | InstantiationException | IllegalAccessException |
                         InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
                stacks.add(stack);
            }
        }
        updated = true;
    }

    public void decrement(Class<? extends Item> item) {
        for(ItemStack stack : stacks) {
            if(stack.getItemClass().equals(item)) {
                stack.decrement();
                updated = true;
                return;
            }
        }
    }

    @Override
    public void tick(float delta) {
        if(updated) {
            RenderSystem renderSystem = Main.game.renderSystem;

            for(int i = 0; i < 5; i++) {
                if(images[i] != null) {
                    renderSystem.guiCollection.removeElement(images[i]);
                    images[i] = null;
                }
            }

            for(int i = 0; i < stacks.size(); i++) {
                if(stacks.get(i).size() > 0) {
                    images[i] = new GuiImage(new Vector2f(POSITION_X + 0.25f + (i * 2.0f), 0.25f), new Vector2f(1.5f, 1.5f));
                    try {
                        images[i].setTexture(renderSystem.getTextureByName((String) stacks.get(i).getItemClass().getField("TEXTURE_NAME").get(null)));
                    } catch (NoSuchFieldException e) {
                        //TODO: load backup texture
                        throw new RuntimeException(e);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                    renderSystem.guiCollection.addElement(images[i]);
                }
            }

            int meshCount = 0;

            List<GuiMesh> meshes = new ArrayList<>();

            for(int i = 0; i < 5; i++) {
                if(images[i] != null) {
                    if (i == 0) {
                        meshes.add(images[i].mesh);
                    } else {
                        if (images[i].mesh != meshes.get(meshCount)) {
                            meshes.add(images[i].mesh);
                        }
                    }
                }
            }

            for(GuiMesh mesh : meshes) {
                mesh.mesh();
            }

            updated = false;
        }
    }
}
