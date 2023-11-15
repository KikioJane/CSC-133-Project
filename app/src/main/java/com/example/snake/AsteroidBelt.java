package com.example.snake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import java.util.ArrayList;
import java.util.Random;

// TODO: make it so apple doesn't spawn on wall?
public class AsteroidBelt implements IGameObject {
    // The location of the initial asteroid on the grid
    // Not in pixels
    private final Point location = new Point();

    // The range of values we can choose from
    // to spawn the first asteroid
    private final Point mSpawnRange;
    private final int mSize;
    private Bitmap mBitmapAsteroid;
    private Bitmap mBitmapSmallAsteroid;
    private ArrayList<Point> wallLocations;

    // An image to represent the apple
    private Bitmap mBitmapApple;
    AsteroidBelt(Context context, Point sr, int s){
        // Make a note of the passed in spawn range
        mSpawnRange = sr;
        // Make a note of the size of an apple
        mSize = s;

        mBitmapAsteroid = BitmapFactory.decodeResource(context.getResources(), R.drawable.bigasteroid);
        // Resize the bitmap
        mBitmapAsteroid = Bitmap.createScaledBitmap(mBitmapAsteroid, s, s, false);

        mBitmapSmallAsteroid = BitmapFactory.decodeResource(context.getResources(), R.drawable.smallasteroids);
        mBitmapSmallAsteroid = Bitmap.createScaledBitmap(mBitmapSmallAsteroid, s, s, false);
    }
    void spawn(Difficulty difficulty){
        //Spawn the wall based on the initial asteroid
        Random random = new Random();
        Point location = new Point(random.nextInt(mSpawnRange.x-1) + 1, random.nextInt(mSpawnRange.y - 1) + 1);
        makeBelt(difficulty, location);
    }

    private void makeBelt(Difficulty difficulty, Point location){
        int wallSize;
        wallLocations = new ArrayList<Point>();
        Random random = new Random();
        if (difficulty == Difficulty.Easy){
            wallSize = random.nextInt(6)+1;
        }
        else if (difficulty == Difficulty.Medium){
            wallSize = random.nextInt(10)+1;
        }
        else{
            wallSize = random.nextInt(15)+8;
        }
        wallLocations.add(location);
        // TODO:  add some error checking for bounds or something. also make sure
        //  they don't overlap themselves? or maybe they can?
        int x = location.x;
        int y = location.y;
        for (int i = 1; i<wallSize;i++){
            int m = randDirection();
            if (m == 0) {
                wallLocations.add(new Point(x+1, y));
                x+=1;
            }else if (m==1){
                wallLocations.add(new Point(x, y+1));
                y+=1;
            }else if (m==2){
                wallLocations.add(new Point(x-1, y));
                x-=1;
            }else {
                wallLocations.add(new Point(x, y-1));
                y-=1;
            }
        }
    }

    private int randDirection(){
        Random random = new Random();
        return random.nextInt(3);
    }
    ArrayList<Point> getLocation(){ return wallLocations; }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        for (int i =0; i < wallLocations.size(); i+=2){
            canvas.drawBitmap(mBitmapAsteroid, wallLocations.get(i).x* mSize, wallLocations.get(i).y* mSize, paint);
        }
        for (int i =1; i < wallLocations.size(); i+=2){
            canvas.drawBitmap(mBitmapSmallAsteroid, wallLocations.get(i).x* mSize, wallLocations.get(i).y* mSize, paint);
        }
    }
}
