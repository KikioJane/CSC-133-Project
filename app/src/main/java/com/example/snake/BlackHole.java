package com.example.snake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

//Blackholes will spawn every x amount of stars eaten
//They will kill the SpaceWorm if it collides with it
public class BlackHole extends GameObject implements IDrawable {
    private Point partnerLocation = new Point();
    private Bitmap mBitmapBlackHole;
    public BlackHole(Context c, Point sr, int size) {
        super(sr, size);
        mBitmapBlackHole = BitmapFactory.decodeResource(c.getResources(), R.drawable.black_hole);
        // Resize the bitmap
        mBitmapBlackHole = Bitmap.createScaledBitmap(mBitmapBlackHole, size, size, false);
    }

    @Override
    public void spawn() {
        //Code here to spawn an instance of a black hole
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        canvas.drawBitmap(mBitmapBlackHole,
                location.x * mSize, location.y * mSize, paint);
    }
}
