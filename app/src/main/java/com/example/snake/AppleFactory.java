package com.example.snake;

import android.content.Context;
import android.graphics.Point;

import java.util.List;

public class AppleFactory extends IGameObjectFactory {
    public AppleFactory(Context context, int NUM_BLOCKS_WIDE, int mNumBlocksHigh, int blockSize,
            List<IGameObject> gameObjects) {
        super(context, NUM_BLOCKS_WIDE, mNumBlocksHigh, blockSize, gameObjects);
    }

    @Override
    IGameObject createObject() {
        Apple apple = new Apple(context, new Point(NUM_BLOCKS_WIDE, mNumBlocksHigh), blockSize);
        gameObjects.add(apple);
        apple.spawn();
        return apple;
    }
}