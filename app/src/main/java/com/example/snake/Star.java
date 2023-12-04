package com.example.snake;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

public abstract class Star extends GameObject implements IDrawable {

    //private static AsteroidBelt mAsteroidBelt;
    protected int points;
    protected int segmentAmount;
    private StarType type;
    public Star(Point sr, int size) {
        super(sr, size);
        isActive = true;
    }
    public void spawn(){
        //Point coord = ValidCoord();
        Point coord = validCoord();
        setLocation(coord.x, coord.y);
        this.isActive = true;
    }
    protected Point validCoord(){
        return AsteroidBelt.validCoord();
    }
    public StarType getType(){ return type; }
    @Override
    public abstract void draw(Canvas canvas, Paint paint);
    public int points(){ return points;}
    public int segmentsLost() { return segmentAmount;}
    public void updateStar(){;};


}
