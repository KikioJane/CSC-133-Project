package com.example.snake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.SurfaceHolder;

import java.util.Random;

class Background {

    private SurfaceHolder mSurfaceHolder;
    private Canvas mCanvas;
    private Bitmap mBitmapBackground;
    private Point sr;
    private int x = 0;
    private int y = 0;
    private int bWidth;
    private int bHeight;


    Background(Context context, Point sr){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        mBitmapBackground = BitmapFactory.decodeResource(context.getResources(), R.drawable.spacebackground);
        bWidth = mBitmapBackground.getWidth();
        bHeight = mBitmapBackground.getHeight();
        this.sr = sr;
    }

    public void draw(Canvas canvas, Paint paint){
        canvas.drawColor(Color.argb(255,10,44,54));
        canvas.drawBitmap(mBitmapBackground, x,y, paint);
    }

    public void resetPlacement(){
        Point p;
        Random rand = new Random();
        int maxWidth = bWidth - sr.x;
        int maxHeight = bHeight - sr.y;
        x = rand.nextInt(maxWidth);
        y = rand.nextInt(maxHeight);
        x *= -1;
        y *= -1;
    }

}
