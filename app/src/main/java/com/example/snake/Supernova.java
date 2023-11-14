package com.example.snake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

//Supernovas will spawn every x amount of stars eaten
//Supernovas will harm the Spaceworm if it tries to eat one (lose a point and segment)
public class Supernova extends GameObject implements IDrawable{
    private Bitmap mBitmapSupernova;
    public Supernova(Context c, Point sr, int size) {
        super(sr, size);
        mBitmapSupernova = BitmapFactory.decodeResource(c.getResources(), R.drawable.supernova);
        // Resize the bitmap
        mBitmapSupernova = Bitmap.createScaledBitmap(mBitmapSupernova, size, size, false);
    }

    @Override
    public void spawn() {
        //Code here to spawn an instance of a supernova
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        canvas.drawBitmap(mBitmapSupernova,
                location.x * mSize, location.y * mSize, paint);
    }
}
