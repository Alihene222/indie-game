package com.alihene.world.gameobject.player.item;

import java.util.ArrayList;
import java.util.List;

public class ItemStack {
    private final Class<? extends Item> itemClass;
    private final List<Item> items;

    public ItemStack(Class<? extends Item> itemClass) {
        this.itemClass = itemClass;
        items = new ArrayList<>();
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public List<Item> getItems() {
        return items;
    }
}
