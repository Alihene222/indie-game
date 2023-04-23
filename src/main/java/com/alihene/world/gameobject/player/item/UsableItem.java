package com.alihene.world.gameobject.player.item;

public abstract class UsableItem extends Item {
    public UsableItem(ItemStack stack) {
        super(stack);
    }

    public void onUse() {
        stack.getItems().remove(this);
    }
}
