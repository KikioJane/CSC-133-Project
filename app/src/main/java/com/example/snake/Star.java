package com.example.snake;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import java.util.Random;

public abstract class Star extends GameObject implements IDrawable {

    private static AsteroidBelt mAsteroidBelt;
    private StarType type;
    public Star(Point sr, int size) {
        super(sr, size);
    }

    public void spawn(){
        Point coord = ValidCoord();
        setLocation(coord.x, coord.y);
    }
    protected Point ValidCoord(){
        Random random = new Random();
        int x = random.nextInt(mSpawnRange.x - 1) + 1;
        int y = random.nextInt(mSpawnRange.y - 1) + 1;
        boolean[][] asteroidMap = mAsteroidBelt.getAsteroidMap();
        asteroidMap[mSpawnRange.x - 1][mSpawnRange.y - 1] = false;
        while(asteroidMap[x][y]) {
            x = random.nextInt(mSpawnRange.x - 1) + 1;
            y = random.nextInt(mSpawnRange.y - 1) + 1;
        }
        return new Point(x,y);
    }
    static void setAsteroidBelt(AsteroidBelt aBelt){
        mAsteroidBelt = aBelt;
    }
    public StarType getType(){ return type; }
    @Override
    public abstract void draw(Canvas canvas, Paint paint);

}
