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
        Random random = new Random();
        int randVal = random.nextInt(5);
        if (randVal == 4){
            type = StarType.blue;
        }
        else if (randVal == 3){
            type = StarType.pink;
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
            default:
                throw new IllegalArgumentException("Unknown star type "+ type);
        }
    }
}
