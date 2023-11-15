package com.example.snake;

import android.content.Context;

import java.util.List;

abstract class IGameObjectFactory {
    protected final Context context;
    protected final int NUM_BLOCKS_WIDE;
    protected final int mNumBlocksHigh;
    protected final int blockSize;

    public IGameObjectFactory(Context context, int NUM_BLOCKS_WIDE, int mNumBlocksHigh,
            int blockSize, List<IGameObject> gameObjects) {
        this.context = context;
        this.NUM_BLOCKS_WIDE = NUM_BLOCKS_WIDE;
        this.mNumBlocksHigh = mNumBlocksHigh;
        this.blockSize = blockSize;
        this.gameObjects = gameObjects;
    }

    protected final List<IGameObject> gameObjects;

    abstract IGameObject createObject();
}
