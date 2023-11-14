package com.example.snake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;

public class Draw {

    private SurfaceHolder mSurfaceHolder;
    private Canvas mCanvas;

    Draw(){

    }

    public Bitmap drawBG1(SurfaceHolder mSurfaceHolder, Context context){

        Paint mPaint = new Paint();

        Bitmap BG1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.smallstarsbackground);
        mPaint.setColor(Color.BLUE);
        return BG1;

    }
}
