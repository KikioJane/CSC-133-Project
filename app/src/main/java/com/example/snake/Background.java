package com.example.snake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.SurfaceHolder;

class Background implements IGameObject {

    private SurfaceHolder mSurfaceHolder;
    private Canvas mCanvas;
    private Bitmap mBitmapBackground;
    private static Background mBackground = null;

    private Background(Context context ){
        mBitmapBackground = BitmapFactory.decodeResource(context.getResources(), R.drawable.starsbackground);
    }

    static Background getBackgroundInstance(Context context){

        // Make a new snake object if it doesn't exist yet
        if(mBackground == null){
            mBackground = new Background(context);
        }
        return mBackground;
    }


    @Override
    public void draw(Canvas canvas, Paint paint){
        canvas.drawBitmap(mBitmapBackground, 0,0, paint);
    }

}
