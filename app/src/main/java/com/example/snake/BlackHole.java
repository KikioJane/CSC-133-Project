package com.example.snake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

// Black holes will spawn every x amount of stars eaten
// They will kill the SpaceWorm if it collides with it
public class BlackHole extends YellowStar implements IDrawable {
    private Bitmap mBitmapBlackHole;
    private final StarType type = StarType.blackhole;

    public BlackHole(Context c, Point sr, int size) {
        super(c, sr, size);
        points = 1;
        segmentAmount = (-1) * points;

        mBitmapBlackHole = BitmapFactory.decodeResource(c.getResources(), R.drawable.blackholepurple);
        // Resize the bitmap
        mBitmapBlackHole = Bitmap.createScaledBitmap(mBitmapBlackHole, size, size, false);
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        canvas.drawBitmap(mBitmapBlackHole,
                location.x * mSize, location.y * mSize, paint);
    }

    public int points(int score){
        if (score < 16) {
            points = 1;
        } else {
            points = score / 10;
        }
        segmentAmount = (-1) * points;
        return points;
    }

    public StarType getType(){
        return type;
    }
}
