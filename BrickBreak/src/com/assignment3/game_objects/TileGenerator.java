package com.assignment3.game_objects;
import java.awt.*;
import java.util.*;

// A class for creating and returning random tile arrangements
public class TileGenerator {
    // The row and column variables
    private int rows ;
    private int columns;
    private Random random = new Random();

    public TileGenerator(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
    }

    // Returns a list of block objects
    public LinkedList<BlockObject> getTiles () {
        LinkedList<BlockObject> ls = new LinkedList<>();
        // The set keeps track of all the points that are visited
        Set<Point> p = new HashSet<>();
        Point p1;
        // Some constants
        int width = Constants.BLOCK_WIDTH;
        int height = Constants.BLOCK_HEIGHT;
        int top_offset = Constants.TOP_OFFSET;

        //The loop keeps on iterating until at least 50% of the 2D array is traversed
        while ((float)ls.size()/(rows*columns) < .5) {
            int i = random.nextInt(rows);
            int j = random.nextInt(columns);
            p1 = new Point(i,j);
            // Avoid duplication
            if (!p.contains(p1)) {
                // Add a new block from the block factory into the list
                ls.add(
                        BlockFactory.getBlockInstance(j * width, i * height + top_offset,
                                width, height, 1 + random.nextInt(3)
                ));
                // Mark the location as visited
                p.add(p1);
            }
        }
        // Return the list
        return ls;
    }

}
