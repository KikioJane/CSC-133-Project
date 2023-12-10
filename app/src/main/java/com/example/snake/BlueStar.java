package com.example.snake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

class BlueStar extends Star implements IDrawable{
    // An image to represent the star
    private Bitmap mBitmapStar;
    private final StarType type = StarType.blue;
    /// Set up the apple in the constructor
    public BlueStar(Context context, Point sr, int s){
        super(sr, s);
        isActive = true;
        points = 1;
        segmentAmount = 1;
        // Load the image to the bitmap
        mBitmapStar = BitmapFactory.decodeResource(context.getResources(), R.drawable.bluestar);

        // Resize the bitmap
        mBitmapStar = Bitmap.createScaledBitmap(mBitmapStar, s, s, false);
    }

    // Draw the star
    @Override
    public void draw(Canvas canvas, Paint paint){
        canvas.drawBitmap(mBitmapStar,
                location.x * mSize, location.y * mSize, paint);
    }

    public StarType getType(){
        return type;
    }

    @Override
    public int points(){ return points; }

}