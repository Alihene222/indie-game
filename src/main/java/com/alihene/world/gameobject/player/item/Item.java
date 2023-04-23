package com.alihene.world.gameobject.player.item;

import com.alihene.Main;
import com.alihene.world.gameobject.player.Player;

public abstract class Item {
    protected final Player player;
    protected final ItemStack stack;

    public Item(ItemStack stack) {
        player = Main.game.world.player;
        this.stack = stack;
    }
}
