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
    public Star(Context context, Point sr, int s){
        super(sr, s);
        // Load the image to the bitmap
        mBitmapStar = BitmapFactory.decodeResource(context.getResources(), R.drawable.apple);

        // Resize the bitmap
        mBitmapStar = Bitmap.createScaledBitmap(mBitmapStar, s, s, false);
    }

    // This is called every time an apple is eaten


    public void spawn(){
        // Choose two random values and place the apple
        Random random = new Random();
        // location.x = random.nextInt(mSpawnRange.x) + 1;
        // location.y = random.nextInt(mSpawnRange.y - 1) + 1;
        setLocation(random.nextInt(mSpawnRange.x-1) + 1, random.nextInt(mSpawnRange.y - 1) + 1);
    }


    // Can be used to set more specific locations for other game features


    // Draw the apple
    @Override
    public void draw(Canvas canvas, Paint paint){
        canvas.drawBitmap(mBitmapStar,
                location.x * mSize, location.y * mSize, paint);

    }

}