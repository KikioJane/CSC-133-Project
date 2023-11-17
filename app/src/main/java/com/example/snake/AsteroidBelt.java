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
        int numberOfBelts;
        int maxBeltLength;
        int wallSize;
        wallLocations = new ArrayList<Point>();
        Random random = new Random();
        if (difficulty == Difficulty.Easy){
            numberOfBelts = 3;
            maxBeltLength = 6;
        }
        else if (difficulty == Difficulty.Medium){
            numberOfBelts = 4;
            maxBeltLength = 10;
        }
        else{
            numberOfBelts = 5;
            maxBeltLength = 14;
        }
        makeBelt(numberOfBelts, maxBeltLength);
    }

    private void makeBelt(int numberOfBelts, int beltSize){
        int wallSize;
        Random random = new Random();
        // To make multiple belts
        for(int i = 0; i < numberOfBelts; i++){
            // Make a random starting location
            int x = random.nextInt(mSpawnRange.x-1) + 1;
            int y = random.nextInt(mSpawnRange.y - 1) + 1;
            // add location of the new starting point
            wallLocations.add(new Point(x,y));
            // Determine the size of the wall
            wallSize = random.nextInt(beltSize)+beltSize/2;
            // Make a wall by randomly choosing segments adjacent to the most recent one?
            for (int j = 1; j<wallSize;j++){
                int m = randDirection();
                if (m == 0 && (x+1 != mSpawnRange.x-1)) {
                    wallLocations.add(new Point(x+1, y));
                    x+=1;
                }else if (m==1 && (y+1 != mSpawnRange.y-1)){
                    wallLocations.add(new Point(x, y+1));
                    y+=1;
                }else if (m==2 && (x-1 != mSpawnRange.x+1)){
                    wallLocations.add(new Point(x-1, y));
                    x-=1;
                }else if (m==3 && (y-1 != mSpawnRange.y+1)){
                    wallLocations.add(new Point(x, y-1));
                    y-=1;
                }
            }
        }
    }
    private void easyBelt(){
        int numberOfBelts = 3;
        Random random = new Random();
        //wallLocations = new ArrayList<Point>();
        int wallSize;

        wallSize = random.nextInt(6)+3;
        for(int i = 0; i < numberOfBelts; i++){
            //fillBelt();
        }
        //fillBelt();
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

     ArrayList<Point> getWallLocations(){
        return wallLocations;
    }

}
