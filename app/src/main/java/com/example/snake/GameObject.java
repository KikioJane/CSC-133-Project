package com.example.snake;

import android.graphics.Bitmap;
import android.graphics.Point;

public abstract class GameObject {
    // The location of the Game Object on the grid
    // Not in pixels
    protected Point location;

    // The range of values we can choose from
    // to spawn an apple
    protected Point mSpawnRange;
    protected int mSize;


    public GameObject (Point sr, int size)
    {
        location = new Point();
        mSpawnRange = sr;
        mSize = size;
    }

    abstract public void spawn();

    void setLocation(int x, int y) {
        location.x = x;
        location.y = y;
    }

    // Let SnakeGame know where the apple is
    // SnakeGame can share this with the snake
    Point getLocation(){
        return location;
    }
}