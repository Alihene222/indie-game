package com.alihene.world.gameobject.player.item;

public class GrassSeedsItem extends UsableItem {
    public static final String TEXTURE_NAME = "grass_seeds";

    public GrassSeedsItem(ItemStack stack) {
        super(stack);
    }

    @Override
    public void onUse() {
        stack.removeItem(this);
    }
}