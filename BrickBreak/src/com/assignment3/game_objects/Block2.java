package com.assignment3.game_objects;

import java.awt.*;

class Block2  extends BlockObject{


    public Block2 (int x, int y, int width, int height, int hits) {

        super(x,y,width,height);
        // The number of hits before this block caves
        this.hits = hits;
    }

    public void paint (Graphics2D g) {
        // Giving the blocks a visible outline
        g.setStroke(new BasicStroke(5));
        // Setting their color and drawing the block
        g.setColor(Color.gray);
        g.fillRect(x,y,width,height);
        // Drawing the outline
        g.setColor(Color.black);
        g.drawRect(x,y,width,height);
        // Painting the number of hits on the block
        g.setFont(new Font("Helvetica Neue", Font.PLAIN, 20));
        g.drawString(""+hits,x+width/3+4,y+height/2+6);
    }

    public int getHits () {
        hits -= 1;
        return hits;
    }
}
