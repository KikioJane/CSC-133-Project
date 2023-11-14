package com.example.snake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;

class Background {

    private SurfaceHolder mSurfaceHolder;
    private Canvas mCanvas;
    private Bitmap mBitmapBackground;
    private static Background mBackground = null;

    public Background(Context context){
        mBitmapBackground = BitmapFactory.decodeResource(context.getResources(), R.drawable.starsbackground);
    }

    public void draw(Canvas canvas, Paint paint){
        canvas.drawColor(Color.argb(255,10,44,54));
        canvas.drawBitmap(mBitmapBackground, 0,0, paint);
    }

}
