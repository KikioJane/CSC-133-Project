package com.example.snake;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.content.Context;
import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ResourceBundle;

public class Renderer {
    private Canvas mCanvas;
    private SurfaceHolder mSurfaceHolder;
    private Paint mPaint;


    Renderer(SurfaceView sh){
        mSurfaceHolder = sh.getHolder();
        mPaint = new Paint();
    }

    // boolean[] = mPaused, mGameRunning, mGameOver
    public void draw(Context context, GameObjectCollection objects, boolean[] states, int mScore) {
        GameObjectIterator itr = objects.createGameObjectIterator();
        // Get a lock on the mCanvas
        if (mSurfaceHolder.getSurface().isValid()) {
            mCanvas = mSurfaceHolder.lockCanvas();

            // Set the size and color of the mPaint for the text


            GameObject obj;
            // draw all the game objects
            while (itr.hasNext()){
                obj = itr.getNext();
                if (obj.checkActive()){
                    if (obj instanceof PauseResume){
                        if (states[0]){
                            if(states[1]){
                                mCanvas.drawColor(Color.argb(127, 0, 0, 0));

                                // Draw resume icon
                                objects.createGameObjectIterator().findPauseResume().drawResume(mCanvas, mPaint);
                                // Draw resume text
                                mPaint.setColor(Color.argb(255, 255, 255, 255));
                                mPaint.setTextSize(60);
                                mCanvas.drawText(context.getResources().getString(R.string.tap_to_resume),
                                        obj.getSpawnRange().x - 530, 95, mPaint);                            } else {
                                // Set the size and color of the mPaint for the text
                                mPaint.setColor(Color.argb(255, 255, 255, 255));

                                // Draw the message
                                if (states[2]) {
                                    // Draw red overlay on screen
                                    mCanvas.drawColor(Color.argb(80, 255, 0, 0));

                                    mPaint.setTextSize(200);
                                    mCanvas.drawText(context.getResources().getString(R.string.game_over),
                                            200, 400, mPaint);

                                    mPaint.setTextSize(100);
                                    mCanvas.drawText(String.format("%s: %d", context.getResources().getString(R.string.score), mScore),
                                            220, 530, mPaint);

                                    mPaint.setTextSize(76);
                                    mCanvas.drawText(context.getResources().getString(R.string.tap_to_play_again),
                                            220, 650, mPaint);
                                } else {
                                    mPaint.setTextSize(200);
                                    mCanvas.drawText(context.getResources().getString(R.string.tap_to_play),
                                            50, 400, mPaint);
                                }
                            }
                        } else {
                            objects.createGameObjectIterator().findPauseResume().drawPause(mCanvas, mPaint);
                        }
                    }
                    else {
                        obj.draw(mCanvas, mPaint);
                    }
                }
            }
            // Set the size and color of the mPaint for the text
            mPaint.setColor(Color.argb(255, 255, 255, 255));
            mPaint.setTextSize(120);

            // Draw the score
            mCanvas.drawText("" + mScore, 20, 120, mPaint);
            // Unlock the mCanvas and reveal the graphics for this frame
            mSurfaceHolder.unlockCanvasAndPost(mCanvas);
        }
    }
}


