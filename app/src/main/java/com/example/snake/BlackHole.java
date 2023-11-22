package com.example.snake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import java.util.Random;

//Blackholes will spawn every x amount of stars eaten
//They will kill the SpaceWorm if it collides with it
public class BlackHole extends GameObject implements IDrawable {
    private Bitmap mBitmapBlackHole;
    private static AsteroidBelt mAsteroidBelt;
    public BlackHole(Context c, Point sr, int size) {
        super(sr, size);
        mBitmapBlackHole = BitmapFactory.decodeResource(c.getResources(), R.drawable.black_hole);
        // Resize the bitmap
        mBitmapBlackHole = Bitmap.createScaledBitmap(mBitmapBlackHole, size, size, false);
    }

    public void spawn(){
        Point coord = ValidCoord();
        setLocation(coord.x, coord.y);
    }

    private Point ValidCoord(){
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

    @Override
    public void draw(Canvas canvas, Paint paint) {
        canvas.drawBitmap(mBitmapBlackHole,
                location.x * mSize, location.y * mSize, paint);
    }

    static void setAsteroidBelt(AsteroidBelt aBelt){
        mAsteroidBelt = aBelt;
    }
}
