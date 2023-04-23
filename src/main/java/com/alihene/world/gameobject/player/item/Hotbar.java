package com.alihene.world.gameobject.player.item;

import com.alihene.world.Tickable;

public class Hotbar implements Tickable {
    private final ItemStack[] stacks;

    public Hotbar()  {
        stacks = new ItemStack[5];
    }

    public ItemStack getItemStack(int index) {
        return stacks[index];
    }

    @Override
    public void tick(float delta) {

    }
}
