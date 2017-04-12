package com.mygdx.game.helper;

/**
 * Created by mohamednagy on 4/2/2017.
 */
public enum Difficulty {
    EASY(Constants.EASY_MODE, Constants.EASY_SPAWNS_PER_SECOND),
    MEDIUM(Constants.MEDIUM_MODE, Constants.MEDIUM_SPAWNS_PER_SECOND),
    HARD(Constants.HARD_MODE, Constants.HARD_SPAWNS_PER_SECOND);

    public String mode;
    public float spawnsPerSecond;

    Difficulty(String mode, float spawnsPerSecond){
        this.mode = mode;
        this.spawnsPerSecond = spawnsPerSecond;
    }
}
