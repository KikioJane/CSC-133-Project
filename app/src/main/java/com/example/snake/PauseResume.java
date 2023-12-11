package com.example.snake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

public class PauseResume extends GameObject{
    Bitmap mPausedBitmap;
    Bitmap mResumeBitmap;
    public PauseResume(Context context, Point sr){
        super(sr, 100);
        mPausedBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.pause_icon);
        mPausedBitmap = Bitmap.createScaledBitmap(mPausedBitmap, 100, 100, false);
        mResumeBitmap = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.resume_icon);
        mResumeBitmap = Bitmap.createScaledBitmap(mResumeBitmap, 100, 100, false);
        isActive = true;
    }

    public void spawn(){}
    @Override
    public void draw(Canvas canvas, Paint paint){}
    public void drawPause(Canvas canvas, Paint paint){
        // Draw pause icon
        canvas.drawBitmap(mPausedBitmap, this.mSpawnRange.x - 125, 25, paint);

    }
    public void drawResume(Canvas canvas, Paint paint){
        // Draw pause icon
        canvas.drawBitmap(mResumeBitmap, this.mSpawnRange.x - 125, 25, paint);

    }
    public boolean checkActive(){ return isActive; }
}
