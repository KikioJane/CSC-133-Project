package com.example.snake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

class YellowStar extends Star implements IDrawable {
    // An image to represent the apple
    private Bitmap mBitmapStar;
    private final StarType type = StarType.yellow;
    /// Set up the apple in the constructor
    public YellowStar(Context context, Point sr, int s){
        super(sr, s);
        isActive = true;
        points = 1;
        segmentAmount = 1;
        // Load the image to the bitmap
        mBitmapStar = BitmapFactory.decodeResource(context.getResources(), R.drawable.yellowstar);

        // Resize the bitmap
        mBitmapStar = Bitmap.createScaledBitmap(mBitmapStar, s, s, false);
    }

    // Draw the star
    @Override
    public void draw(Canvas canvas, Paint paint){
        canvas.drawBitmap(mBitmapStar,
                location.x * mSize, location.y * mSize, paint);
    }
    @Override
    public void updateStar(){}

    //public StarType getType(){ return type; }
    @Override
    public int points(){ return points; }

    public StarType getType(){
        return type;
    }


}