package com.example.snake;

import android.content.Context;
import android.graphics.Point;

import java.util.Random;

public class StarFactory extends IGameObjectFactory {
    public StarFactory(Context context, int NUM_BLOCKS_WIDE, int mNumBlocksHigh, int blockSize) {
        super(context, NUM_BLOCKS_WIDE, mNumBlocksHigh, blockSize);
    }

    @Override
    Star createObject() {
        StarType type;
        Star star;
        // bound should be set to 6 normally
        // 4 is for demo purposes
        int bound = 4;
        Random random = new Random();
        if (this.difficulty == Difficulty.Medium)
            bound = 13;
        if (this.difficulty == Difficulty.Hard)
            bound = 20;
        int randVal = random.nextInt(bound);
        if (randVal == 1){
            type = StarType.blue;
        }
        else if (randVal == 2){
            type = StarType.pink;
        }
        else if (randVal == 3){
            type = StarType.supernova;
        }
        else{
            type = StarType.yellow;
        }
        star = createStar(type);
        star.spawn();
        return star;
    }

    public Star createStar(StarType type){
        switch(type){
            case yellow:
                return new YellowStar(context, new Point(NUM_BLOCKS_WIDE, mNumBlocksHigh), blockSize);
            case blue:
                return new BlueStar(context, new Point(NUM_BLOCKS_WIDE, mNumBlocksHigh), blockSize);
            case pink:
                return new PinkStar(context, new Point(NUM_BLOCKS_WIDE, mNumBlocksHigh), blockSize);
            case supernova:
                return new Supernova(context, new Point(NUM_BLOCKS_WIDE, mNumBlocksHigh), blockSize);
            default:
                throw new IllegalArgumentException("Unknown star type "+ type);
        }
    }
}
