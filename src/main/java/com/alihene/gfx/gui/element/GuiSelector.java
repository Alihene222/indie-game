package com.alihene.gfx.gui.element;

import com.alihene.Main;
import com.alihene.gfx.Window;
import com.alihene.gfx.gui.GuiCollection;
import com.alihene.world.gameobject.tile.Tile;
import org.joml.Vector2f;

import java.util.Optional;

public class GuiSelector extends GuiElement {
    private final Vector2f lastPos;
    public GuiCollection guiCollection;

    private boolean hidden;

    public GuiSelector(Vector2f pos, Vector2f size) {
        super(pos, size);
        lastPos = new Vector2f(0.0f, 0.0f);
        hidden = true;
    }

    public Optional<Tile> getTile() {
        return Main.game.world.getTileAtCoordinates(
                (float) Math.floor(pos.x) - 8.0f + (float) Math.floor(Main.game.world.player.pos.x),
                (float) Math.floor(pos.y) - 8.0f + (float) Math.floor(Main.game.world.player.pos.y));
    }

    @Override
    public void tick(float delta) {
        Window window = Main.game.window;

        Optional<Tile> tileOptional = Main.game.world.tileMap.tiles.stream().filter(t ->
                t.pos.x - (Main.game.world.player.pos.x % 2.0f) + 1.0f < (window.getMousePosition().x / 32.0f) &&
                t.pos.x + t.size.x - (Main.game.world.player.pos.x % 2.0f) + 1.0f > (window.getMousePosition().x / 32.0f) &&
                t.pos.y - (Main.game.world.player.pos.y % 2.0f) + 1.0f < ((-window.getMousePosition().y + 640.0f) / 32.0f) &&
                t.pos.y + t.size.y - (Main.game.world.player.pos.y % 2.0f) + 1.0f > ((-window.getMousePosition().y + 640.0f) / 32.0f)
        ).findFirst();
        tileOptional.ifPresent(tile -> {
            lastPos.x = pos.x;
            lastPos.y = pos.y;
            float xDifference = (Main.game.world.player.pos.x % 2.0f) - 1.0f;
            float yDifference = (Main.game.world.player.pos.y % 2.0f) - 1.0f;
            pos.x = tile.pos.x - xDifference;
            pos.y = tile.pos.y - yDifference;

            if(pos.x != lastPos.x || pos.y != lastPos.y) {
                updateMesh();
            }
        });
    }
}
