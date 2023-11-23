package com.example.snake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import java.util.Random;

class Star extends GameObject implements IDrawable {
    // An image to represent the apple
    private Bitmap mBitmapStar;
    /// Set up the apple in the constructor
    private static AsteroidBelt mAsteroidBelt;
    public Star(Context context, Point sr, int s){
        super(sr, s);
        // Load the image to the bitmap
        mBitmapStar = BitmapFactory.decodeResource(context.getResources(), R.drawable.yellowstar);

        // Resize the bitmap
        mBitmapStar = Bitmap.createScaledBitmap(mBitmapStar, s, s, false);
    }

    // This is called every time an apple is eaten


    public void spawn(){
        Point coord = ValidCoord();
        setLocation(coord.x, coord.y);
    }

    // To get a coordinate that is not occupied by the asteroid belt
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

    // Draw the apple
    @Override
    public void draw(Canvas canvas, Paint paint){
        canvas.drawBitmap(mBitmapStar,
                location.x * mSize, location.y * mSize, paint);
    }

    static void setAsteroidBelt(AsteroidBelt aBelt){
        mAsteroidBelt = aBelt;
    }

}