package com.assignment3.game_objects;

import java.awt.*;

/*
* This class is analogous to Java's Object class in that every other
* component relevant to this game extends from it. It has, therefore, most
* of the common methods.
* */
public abstract class GameObject {
    // The object's coordinates on the panel
    protected int x;
    protected int y;

    // The height and wight of the 2D object
    protected int height;
    protected int width;

    // Default constructor
    public GameObject() {}

    // Overloaded constructor
    public GameObject (int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void moveTo (int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    // Used for detecting collisions
    public Rectangle getBound () {
        return new Rectangle(x,y,width,height);
    }

    // A must in all game objects
    public abstract void paint(Graphics2D g);


}
