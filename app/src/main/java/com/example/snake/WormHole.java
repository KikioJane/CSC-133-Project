package com.example.snake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

//WormHoles will spawn every x amount of stars eaten
//Each wormhole will have a partner location that will
// be where the wormhole will transport the SpaceWorm
public class WormHole extends GameObject implements IGameObject{
    private Point partnerLocation = new Point();
    private Bitmap mBitmapWormHole;
    public WormHole(Context c, Point sr, int size) {
        super(sr, size);
        mBitmapWormHole = BitmapFactory.decodeResource(c.getResources(), R.drawable.worm_hole);
        // Resize the bitmap
        mBitmapWormHole = Bitmap.createScaledBitmap(mBitmapWormHole, size, size, false);
    }

    @Override
    public void spawn() {
        //Code here to spawn 2 portal instances
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        canvas.drawBitmap(mBitmapWormHole,
                location.x * mSize, location.y * mSize, paint);
    }
}
