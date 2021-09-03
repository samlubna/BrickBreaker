package com.assignment3.game_objects;

/*
* The only discerning quality is the number of hits a block can take.
* This factory groups all types of blocks into one class which is used by the
* game board to dynamically request different types.
* */
public class BlockFactory {
    public static BlockObject getBlockInstance (int x,int y, int width, int height, int hit_points) {
        if (hit_points == 1)
            return new Block(x,y,width,height,1);
        else if (hit_points == 2)
          return new Block2(x,y,width,height,2);
        return new Block3(x,y,width,height,3);
    }
}
