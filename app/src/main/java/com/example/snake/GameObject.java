package com.example.snake;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

public abstract class GameObject {
    // The location of the Game Object on the grid
    // Not in pixels
    protected Point location;

    // The range of values we can choose from
    // to spawn an apple
    protected Point mSpawnRange;
    protected int mSize;
    protected boolean isActive = false;

    public GameObject (Point sr, int size)
    {
        location = new Point();
        mSpawnRange = sr;
        mSize = size;
    }

    abstract public void spawn();
    abstract public void draw(Canvas canvas, Paint paint);

    void setLocation(int x, int y) {
        location.x = x;
        location.y = y;
    }

    // Let SnakeGame know where the apple is
    // SnakeGame can share this with the snake
    Point getLocation(){
        return location;
    }
    // see if the object is active
    boolean checkActive(){ return isActive; }
    void setInactive() {
        isActive = false;
    }
    void setActive() {
        isActive = false;
    }
    Point getSpawnRange() { return mSpawnRange; }
}
