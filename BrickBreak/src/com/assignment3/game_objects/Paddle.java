package com.assignment3.game_objects;

import java.awt.*;
import java.awt.event.MouseEvent;

public class Paddle extends GameObject {

    private static Paddle singletonInstance;

    private Paddle (int x, int y, int width, int height) {
        super(x,y,width,height);
    }

    public static Paddle getInstance () {
        if (singletonInstance == null) {
            singletonInstance = new Paddle(0,0,Constants.PADDLE_WIDTH,Constants.PADDLE_HEIGHT);
            return singletonInstance;
        }
        return singletonInstance;
    }
    public void paint (Graphics2D g) {
        g.setColor(Color.WHITE);
        g.fillRect(x,y,width,height);

    }

    // Moves the paddle through the mouse cursor
    public void mouseDragged (MouseEvent m) {
        x = m.getX() - width/2;
        // Put boundaries here
        if (x < 0)
            x = 0;
        else if (x+width >Constants.BOARD_WIDTH)
            x = Constants.BOARD_WIDTH-width;
    }
}
