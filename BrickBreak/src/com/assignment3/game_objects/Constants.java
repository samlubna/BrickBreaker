package com.assignment3.game_objects;
/*
* A way of grouping some constants with having to go over each line of
* code to make changes if needed.
* */
public class Constants {

    // The board's and J-frame's dimensions
    public static final int BOARD_HEIGHT = 700;
    public static final int BOARD_WIDTH = 700;
    // The Paddle's dimensions
    public static final int PADDLE_WIDTH = 100;
    public static final int PADDLE_HEIGHT = 12;
    // The ball's dimensions
    public static final int BALL_HEIGHT = 22;
    public static final int BALL_WIDTH = 22;
    // The block's dimensions
    public static final int BLOCK_HEIGHT = 40;
    public static final int BLOCK_WIDTH = 50;
    // The y-axis offset to leave some space for the messages
    public static final int TOP_OFFSET = 3 * BLOCK_HEIGHT;
    // Player stats
    public static boolean PLAYER_LOST = false;
    public static int PLAYER_LIFE = 3;
    // The ball's speed
    public static final int BALL_X_SPEED = 7;
    public static final int BALL_Y_SPEED = 6;

}
