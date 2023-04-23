package com.alihene.world.gameobject.player.item;

import java.util.ArrayList;
import java.util.List;

public class ItemStack {
    private final Class<? extends Item> itemClass;
    private final List<Item> items;

    private boolean isHotbar = true;

    public ItemStack(Class<? extends Item> itemClass) {
        this.itemClass = itemClass;
        items = new ArrayList<>();
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public void decrement() {
        items.remove(items.size() - 1);
    }

    public List<Item> getItems() {
        return items;
    }

    public int size() {
        return items.size();
    }

    public boolean isHotbar() {
        return isHotbar;
    }

    public Class<? extends Item> getItemClass() {
        return itemClass;
    }
}
