package com.assignment3.game_objects;
import java.awt.*;

public class Ball extends GameObject{

    private int dx;
    private int dy;
    private final int x_speed = Constants.BALL_X_SPEED;
    private final int y_speed = Constants.BALL_Y_SPEED;

    public Ball (int x, int y, int width, int height)  {
        super(x,y,width,height);
        dy = y_speed;
        dx = 0;

    }

    public void paint (Graphics2D g) {
        g.setColor(new Color(226, 88, 34));
        g.fillOval(x,y,width,height);
    }

    public void move (Paddle p) {

        float B_centre = x + (float)width/2;
        float P_centre = p.getX() + (float)p.getWidth()/2;

        // Paddle collisions (pseudo control)
        if (getBound().intersects(p.getBound()) && x + width > p.getX() && y+height < p.getY()+5) {
            if (B_centre <= P_centre -10) {
                if (dx == 0) dx = -1*x_speed;
                float diff = (P_centre - B_centre) / ((float) p.getWidth() / 2);
                dx = Math.round(x_speed*diff) * ((dx > 0) ? 1 : -1);

            }
            else if (B_centre > P_centre + 10)
            {
                if (dx == 0) dx = x_speed;
                float diff = (B_centre - P_centre) / ((float) p.getWidth()/2);
                dx = Math.round(x_speed*diff) * ((dx > 0) ? 1 : -1);

            }
                dy*=-1;
        }
        else {
            // Wall collisions
            if (x + dx < 0 || x + dx + width > Constants.BOARD_WIDTH)
                dx *= -1;
            else if (y + dy < Constants.BLOCK_HEIGHT)
                dy *= -1;
        }
            x += dx;
            y += dy;

            // Has the ball fallen past the floor
        if (y > Constants.BOARD_HEIGHT) {
            Constants.PLAYER_LOST = true;
            Constants.PLAYER_LIFE -= 1;
            resetSpeed();
        }

    }

    public void collision (Rectangle bl) {

        // There are some clipping issues due to frame rates which may lead to wonky ball physics.
            float ballCentre = y + (float)height/2;
            if (ballCentre < bl.getY() || ballCentre > bl.getY() + bl.getHeight())
                dy *= -1;
            else
                dx *= -1;
    }
    public void resetSpeed () {
        dy = y_speed;
        dx = 0;
    }
    

}
