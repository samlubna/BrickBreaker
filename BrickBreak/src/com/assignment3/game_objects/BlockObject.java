package com.assignment3.game_objects;

import java.awt.*;

/*
* As there's more than one type of block, it is possible they may have some
* common behaviour not exhibited by other objects. Hence, the block object
* builds on top of the game object and can be used to add more nuanced abilities
* */
public abstract class BlockObject extends GameObject {
    protected int hits;
    public BlockObject () {}
    public BlockObject (int x, int y, int width, int height) {
        super(x,y,width,height);
    }

    // This method is shared only by the tiles
    public int getHits () {
        return 0;
    }
}
