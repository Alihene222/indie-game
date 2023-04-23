package com.alihene.world.gameobject.player.item;

import com.alihene.world.Tickable;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class Inventory implements Tickable {
    private final List<ItemStack> stacks;
    public Inventory() {
        stacks = new ArrayList<>();
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
        boolean added = false;

        for(ItemStack stack : stacks) {
            if(stack.getItemClass().equals(item)) {
                try {
                    stack.addItem(item.getConstructor(ItemStack.class).newInstance(stack));
                    added = true;
                } catch(NoSuchMethodException | InstantiationException | IllegalAccessException |
                        InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        if(!added) {
            if(stacks.size() < 27) {
                ItemStack stack = new ItemStack(item);
                try {
                    stack.addItem(item.getConstructor(ItemStack.class).newInstance(stack));
                } catch (NoSuchMethodException | InstantiationException | IllegalAccessException |
                         InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
                stacks.add(stack);
            }
        }
    }

    @Override
    public void tick(float delta) {
        // Key presses
    }
}
