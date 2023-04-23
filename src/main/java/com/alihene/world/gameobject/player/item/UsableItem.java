package com.alihene.world.gameobject.player.item;

public abstract class UsableItem extends Item {
    public UsableItem(ItemStack stack) {
        super(stack);
    }

    public void onUse() {
        if(stack.isHotbar()) {
            stack.getItems().remove(this);
        }
    }
}
