package com.example.snake;

import android.content.Context;
import android.graphics.Point;

import java.util.List;

public class StarFactory extends IGameObjectFactory {
    public StarFactory(Context context, int NUM_BLOCKS_WIDE, int mNumBlocksHigh, int blockSize) {
        super(context, NUM_BLOCKS_WIDE, mNumBlocksHigh, blockSize);
    }

    @Override
    Star createObject() {
        Star star = new Star(context, new Point(NUM_BLOCKS_WIDE, mNumBlocksHigh), blockSize);
        //gameObjects.addGameObject(star);
        star.spawn();
        return star;
    }
}