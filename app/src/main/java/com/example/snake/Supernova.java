package com.example.snake;

import static java.lang.Math.round;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

//Supernovas will spawn every x amount of stars eaten
//Supernovas will harm the Spaceworm if it tries to eat one (lose a point and segment)
// Supernovas are worth 3 points. They jump around after a certain amount of time.
// if the worm didn't eat the supernova after 2 jumps, the supernova
// is replaced with a normal star.
public class Supernova extends Star implements IDrawable {
    private Bitmap mBitmapSupernova;
    private int timeCounter = 0;
    private int jumpCounter=0;
    private final StarType type = StarType.supernova;
    public Supernova(Context c, Point sr, int size) {
        super(sr, size);
        isActive = true;
        points = 3;
        segmentAmount = 3;
        mBitmapSupernova = BitmapFactory.decodeResource(c.getResources(), R.drawable.supernova);
        // Resize the bitmap
        mBitmapSupernova = Bitmap.createScaledBitmap(mBitmapSupernova, size*2, size*2, false);
    }


    /*public void spawn() {
        Point coord = super.validCoord();
        setLocation(coord.x, coord.y);
        this.isActive = true;
    }*/

    @Override
    public void draw(Canvas canvas, Paint paint) {
        int xcoord = (int)round((location.x-0.5)*mSize);
        int ycoord = (int)round((location.y-0.5)*mSize);
        if (isActive){
            canvas.drawBitmap(mBitmapSupernova,
                    xcoord, ycoord, paint);
        }
    }

    @Override
    public void updateStar(){
        if (timeCounter == 30){    // if number of frames has been reached
            timeCounter = 0;
            this.spawn();                // supernova should jump
            timeCounter++;
            jumpCounter++;          // increase jump counter
            if(jumpCounter ==3 ){
                isActive = false;   // remove from map at next update
                jumpCounter = 0;    // reset jump counter
            }
        }
        else{
            timeCounter++;
        }
    }
    public StarType getType(){
        return type;
    }
    boolean checkActive(){ return isActive; }
}
