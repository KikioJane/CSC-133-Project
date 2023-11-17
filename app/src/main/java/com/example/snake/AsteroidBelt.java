package com.example.snake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import java.util.ArrayList;
import java.util.Random;

public class AsteroidBelt extends GameObject implements IDrawable{
    // The location of the initial asteroid on the grid
    // Not in pixels
    private final Point location = new Point();
    private Bitmap mBitmapAsteroid;
    private Bitmap mBitmapSmallAsteroid;
    // used for drawing.
    private ArrayList<Point> wallLocations;
    // used for determining which spaces are occupied by asteroids
    private boolean[][] asteroidMap;
    private Difficulty difficulty;

    // An image to represent the apple
    private Bitmap mBitmapApple;
    AsteroidBelt(Context context, Point sr, int s, Difficulty d){
        super(sr, s);
        // Set difficulty level
        difficulty = d;
        // make asteroidMap the size of the available game blocks
        asteroidMap = new boolean[mSpawnRange.x][mSpawnRange.y];
        mBitmapAsteroid = BitmapFactory.decodeResource(context.getResources(), R.drawable.bigasteroid);
        // Resize the bitmap
        mBitmapAsteroid = Bitmap.createScaledBitmap(mBitmapAsteroid, s, s, false);

        mBitmapSmallAsteroid = BitmapFactory.decodeResource(context.getResources(), R.drawable.smallasteroids);
        mBitmapSmallAsteroid = Bitmap.createScaledBitmap(mBitmapSmallAsteroid, s, s, false);
    }

    public void spawn(){
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
        // To make multiple belts
        for(int i = 0; i < numberOfBelts; i++){
            Point p = new Point(0,0);
            // Make a random starting location
            int x = randomInt(mSpawnRange.x - 3) + 1;
            int y = randomInt(mSpawnRange.y - 3) + 1;
            // add location of the new starting point
            wallLocations.add(new Point(x, y ));
            asteroidMap[x][y] = true;
            // Determine the size of the wall
            wallSize = randomInt(beltSize)+beltSize/2;
            // Make a wall by randomly choosing segments adjacent to the most recent one?
            // Asteroids cannot touch edge blocks of screen.
            for (int j = 1; j<wallSize;j++){
                int m = randomInt(4);
                if (m == 0 && x+1 < mSpawnRange.x-2 && x+1 > 0) {
                    if (wallLocations.contains(new Point(x+1, y ))==false){
                        wallLocations.add(new Point(x+1, y ));
                    }
                    asteroidMap[x+1][y] = true;
                    x+=1;
                }else if (m==1 && y+1 < mSpawnRange.y-2 && y+1 > 0){
                    if (wallLocations.contains(new Point(x, y+1 ))==false){
                        wallLocations.add(new Point(x, y+1 ));
                    }
                    asteroidMap[x][y+1] = true;
                    y+=1;
                }else if (m==2 && x-1 < mSpawnRange.x-2 && x-1 > 0){
                    if (wallLocations.contains(new Point(x-1, y ))==false){
                        wallLocations.add(new Point(x-1, y ));
                    }
                    asteroidMap[x-1][y] = true;
                    x-=1;
                }else if (m==3 && y-1 < mSpawnRange.y-2 && y-1 > 0){
                    if (wallLocations.contains(new Point(x, y - 1))==false) {
                        wallLocations.add(new Point(x, y - 1));
                    }
                    asteroidMap[x][y-1] = true;
                    y-=1;
                }
            }
        }
        emptyCenterScreen();
    }

    private void emptyCenterScreen(){
        int yClearLow = mSpawnRange.y/2 - mSpawnRange.y/10;
        int yClearHigh = mSpawnRange.y/2 + mSpawnRange.y/10;
        for (int i=19; i < 30; i++){
            for (int j=yClearLow; j<= yClearHigh; j++) {
                asteroidMap[i][j] = false;
                if(wallLocations.contains(new Point(i,j))){
                    Point p = new Point(i,j);
                    wallLocations.remove(new Point(i,j));
                }
            }
        }
    }

    private int randomInt(int range){
        Random random = new Random();
        return random.nextInt(range);
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        for (int i =0; i < wallLocations.size(); i+=2){
            canvas.drawBitmap(mBitmapAsteroid, wallLocations.get(i).x* mSize, wallLocations.get(i).y* mSize, paint);
        }
        for (int i =1; i < wallLocations.size(); i+=2) {
            canvas.drawBitmap(mBitmapSmallAsteroid, wallLocations.get(i).x * mSize, wallLocations.get(i).y * mSize, paint);
        }
    }
    
    public boolean[][] getAsteroidMap() { return asteroidMap;}

}
