package com.alihene.world.gameobject.player.item;

import com.alihene.world.Tickable;

public class Inventory implements Tickable {
    private final ItemStack[] stacks;

    public Inventory() {
        stacks = new ItemStack[27];
    }

    public ItemStack getItemStack(int index) {
        return stacks[index];
    }

    @Override
    public void tick(float delta) {
        // Key presses
    }
}
