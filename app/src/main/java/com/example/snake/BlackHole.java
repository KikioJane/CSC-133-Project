package com.example.snake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

//Blackholes will spawn every x amount of stars eaten
//They will kill the SpaceWorm if it collides with it
public class BlackHole extends YellowStar implements IDrawable {
    private Bitmap mBitmapBlackHole;
    private final StarType type = StarType.blackhole;


    public BlackHole(Context c, Point sr, int size) {
        super(c, sr, size);
        mBitmapBlackHole = BitmapFactory.decodeResource(c.getResources(), R.drawable.blackholepurple);
        // Resize the bitmap
        mBitmapBlackHole = Bitmap.createScaledBitmap(mBitmapBlackHole, size, size, false);
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        canvas.drawBitmap(mBitmapBlackHole,
                location.x * mSize, location.y * mSize, paint);
    }

    public StarType getType(){
        return type;
    }
}
