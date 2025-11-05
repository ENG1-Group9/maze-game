package io.github.eng1group9.entities;

import java.util.List;

import io.github.eng1group9.Main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import io.github.eng1group9.systems.ToastSystem;

/**
 * Handles everything connected to the player.
 * @param startPos The players start positon.
 * @author Mat and Max.
 */
public class Player extends MovingEntity {

    private boolean hasExitKey = false;
    private boolean hasChestRoomKey = false;

    public Player(Vector2 startPos, float speed) {
        super(new Texture("Characters/playerAnimations.png"), new int[] {4, 4,4,4} , 32, 32, speed);
        setPosition(startPos);
        setScale(2);
    }

    public boolean hasExitKey() {
        return hasExitKey;
    }

    public void setHasExitKey(Boolean bool) {
        ToastSystem.addToast("You found the exit key!");
        hasExitKey = bool;
    }
    public boolean hasChestRoomKey() {
        return hasChestRoomKey;
    }

    public void setHasChestRoomKey(Boolean bool) {
        hasChestRoomKey = bool;

        if (bool) {
            Main.instance.deleteKeyTile();
            ToastSystem.addToast("You found the key!");
        }
    }
}
