package com.example.snake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import java.util.Random;

//WormHoles will spawn every x amount of stars eaten
//Each wormhole will have a partner location that will
// be where the wormhole will transport the SpaceWorm
public class WormHole extends GameObject implements IDrawable {
    private Point partnerLocation = new Point();
    private Bitmap mBitmapWormHole;
    public WormHole(Context c, Point sr, int size) {
        super(sr, size);
        mBitmapWormHole = BitmapFactory.decodeResource(c.getResources(), R.drawable.worm_hole);
        // Resize the bitmap
        mBitmapWormHole = Bitmap.createScaledBitmap(mBitmapWormHole, size, size, false);
    }

    public Point getPartnerLocation()
    {
        return partnerLocation;
    }
    void setLocation(int mx, int my, int px, int py) {
        super.setLocation(mx, my);
        partnerLocation.x = px;
        partnerLocation.y = py;
    }

    @Override
    public void spawn() {
        Random random = new Random();
        int mx = random.nextInt(mSpawnRange.x - 1) + 1;
        int my = random.nextInt(mSpawnRange.y - 1) + 1;
        int px = random.nextInt(mSpawnRange.x - 1) + 1;
        int py = random.nextInt(mSpawnRange.y - 1) + 1;
        //Code here to spawn 2 portal instances
        setLocation(mx, my, px, py);
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        canvas.drawBitmap(mBitmapWormHole,
                location.x * mSize, location.y * mSize, paint);
        canvas.drawBitmap(mBitmapWormHole,
                partnerLocation.x * mSize, partnerLocation.y * mSize, paint);
    }
}
