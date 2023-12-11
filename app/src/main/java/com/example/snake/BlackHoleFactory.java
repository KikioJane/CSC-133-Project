package com.example.snake;

import android.content.Context;
import android.graphics.Point;

// Factory class for Black Hole
public class BlackHoleFactory extends IGameObjectFactory{
    private int count = 0;
    public BlackHoleFactory(Context context, int NUM_BLOCKS_WIDE, int mNumBlocksHigh, int blockSize) {
        super(context, NUM_BLOCKS_WIDE, mNumBlocksHigh, blockSize);
    }

    @Override
    BlackHole createObject() {
        BlackHole blackHole = new BlackHole(context, new Point(NUM_BLOCKS_WIDE, mNumBlocksHigh), blockSize);
        blackHole.spawn();
        count++;
        return blackHole;
    }

    public int getCount(){return count;}
    public void setCount(int count){this.count = count;}

}