package com.example.snake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

class BlueStar extends Star {
    // An image to represent the star
    private Bitmap mBitmapStar;
    private final StarType type = StarType.blue;
    /// Set up the apple in the constructor
    public BlueStar(Context context, Point sr, int s){
        super(sr, s);
        // Load the image to the bitmap
        mBitmapStar = BitmapFactory.decodeResource(context.getResources(), R.drawable.bluestar);

        // Resize the bitmap
        mBitmapStar = Bitmap.createScaledBitmap(mBitmapStar, s, s, false);
    }


    public void spawn(){
        Point coord = super.validCoord();
        setLocation(coord.x, coord.y);
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
}