package com.example.snake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import java.util.ArrayList;
import java.util.Random;

public class AsteroidCluster {
    private final Point location = new Point();
    private ArrayList<Point> wallLocations;
    private Bitmap mBitmapAsteroid;
    private Bitmap mBitmapSmallAsteroid;
    private Point mSpawnRange;
    private Random random = new Random();

    AsteroidCluster(Context c, Point sr, int s) {
        mSpawnRange = sr;
        mBitmapAsteroid = BitmapFactory.decodeResource(c.getResources(), R.drawable.bigasteroid);
        // Resize the bitmap
        mBitmapAsteroid = Bitmap.createScaledBitmap(mBitmapAsteroid, s, s, false);

        mBitmapSmallAsteroid = BitmapFactory.decodeResource(c.getResources(), R.drawable.smallasteroids);
        mBitmapSmallAsteroid = Bitmap.createScaledBitmap(mBitmapSmallAsteroid, s, s, false);

    }

    // creates an asteroid cluster
    public boolean[][] spawn(int clusterSize, Point startingPoint, boolean[][] asteroidMap) {
        int x = startingPoint.x;
        int y = startingPoint.y;
        // Determine the size of the cluster
        int size = random.nextInt(clusterSize)+clusterSize/2;
        wallLocations = new ArrayList<>();
        // make a cluster that is of size number of asteroids
        for (int i = 0; i < size; i++) {
            int m = random.nextInt(4);
            if (m == 0 && x + 1 < mSpawnRange.x - 3 && (x + 1) > 0) {
                // For whatever reason, it just wouldn't work when I tried "!wallLocations..."
                // So had to use "... == false" instead. Not sure why.
                if (wallLocations.contains(new Point(x + 1, y)) == false) {
                    wallLocations.add(new Point(x + 1, y));
                }
                asteroidMap[x + 1][y] = true;
                x += 1;
            } else if (m == 1 && y + 1 < mSpawnRange.y - 3 && (y + 1) > 0) {
                if (wallLocations.contains(new Point(x, y + 1)) == false) {
                    wallLocations.add(new Point(x, y + 1));
                }
                asteroidMap[x][y + 1] = true;
                y += 1;
            } else if (m == 2 && x - 1 < mSpawnRange.x - 3 && (x - 1) > 0) {
                if (wallLocations.contains(new Point(x - 1, y)) == false) {
                    wallLocations.add(new Point(x - 1, y));
                }
                asteroidMap[x - 1][y] = true;
                x -= 1;
            } else if (m == 3 && y - 1 < mSpawnRange.y - 3 && (y - 1) > 0) {
                if (wallLocations.contains(new Point(x, y - 1)) == false) {
                    wallLocations.add(new Point(x, y - 1));
                }
                asteroidMap[x][y - 1] = true;
                y -= 1;
            }
        }
        return asteroidMap;
    }

    // draws the asteroid cluster
    public void draw(Canvas canvas, Paint paint, int mSize) {
        // alternate between small and large asteroid bitmaps
        for (int i = 0; i < wallLocations.size(); i += 2) {
            canvas.drawBitmap(mBitmapAsteroid, wallLocations.get(i).x * mSize, wallLocations.get(i).y * mSize, paint);
        }
        for (int i = 1; i < wallLocations.size(); i += 2) {
            canvas.drawBitmap(mBitmapSmallAsteroid, wallLocations.get(i).x * mSize, wallLocations.get(i).y * mSize, paint);
        }

    }
    // remove asteroids
    public void clearAsteroid(Point p){
        wallLocations.remove(p);
    }
}
