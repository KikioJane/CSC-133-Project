// New SoundManager class to make the SnakeGame class have
// less responsibility. The Sound manager class has a single
// responsibility of dealing with the sounds that the game
// uses. It encapsulates this responsibility by only playing
// the sounds by way of methods.


package com.example.snake;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import java.io.IOException;


public class SoundManager {
    // For playing sounds
    private static SoundPool mSP = null;
    private static int mEat_ID = -1;
    private static int mAsteroidCrashID = -1;
    private static int mEdgeCrashID = -1;
    private static int mPowerUp1ID = -1;
    private static int mPowerUp2ID = -1;
    private static int mPowerUp3ID = -1;
    private static int mPowerUp4ID = -1;
    private static int mHit1ID = -1;

    private static int mInvisiGlowID = -1;


    public static void InitializeSoundManager(Context context) {
        // Initialize the SoundPool
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();

            mSP = new SoundPool.Builder()
                    .setMaxStreams(5)
                    .setAudioAttributes(audioAttributes)
                    .build();
        } else {
            mSP = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        }
        // Open each of the sound files in turn
        // and load them in to Ram ready to play
        // The try-catch blocks handle when this fails
        // and is required.
        try {
            AssetManager assetManager = context.getAssets();
            AssetFileDescriptor descriptor;

            // Prepare the sounds in memory
            descriptor = assetManager.openFd("get_apple.ogg");
            mEat_ID = mSP.load(descriptor, 0);

            descriptor = assetManager.openFd("asteroidDeath.ogg");
            mAsteroidCrashID = mSP.load(descriptor, 0);

            descriptor = assetManager.openFd("edgeDeath.wav");
            mEdgeCrashID = mSP.load(descriptor, 0);

            descriptor = assetManager.openFd("powerUp1.wav");
            mPowerUp1ID = mSP.load(descriptor, 0);
            descriptor = assetManager.openFd("powerUp2.wav");
            mPowerUp2ID = mSP.load(descriptor, 0);
            descriptor = assetManager.openFd("powerUp3.wav");
            mPowerUp3ID = mSP.load(descriptor, 0);
            descriptor = assetManager.openFd("powerUp4.wav");
            mPowerUp4ID = mSP.load(descriptor, 0);

            descriptor = assetManager.openFd("hit1.wav");
            mHit1ID = mSP.load(descriptor, 0);

            descriptor = assetManager.openFd("invisibleGlow2.wav");
            mInvisiGlowID = mSP.load(descriptor, 0);

        } catch (IOException e) {
            // Error
        }

    }
    // methods to access sounds
    public static void playEatSound(){
        mSP.play(mEat_ID, 1, 1, 0, 0, 1);
    }
    public static void playAsteroidCrashSound(){ mSP.play(mAsteroidCrashID, 1, 1, 0, 0, 1); }
    public static void playEdgeCrashSound(){ mSP.play(mEdgeCrashID, 1, 1, 0, 0, 1); }
    private static void playPowerUp1Sound(){ mSP.play(mPowerUp1ID, 1, 1, 0, 0, 1); }
    private static void playPowerUp2Sound(){ mSP.play(mPowerUp2ID, 1, 1, 0, 0, 1); }
    private static void playPowerUp3Sound(){ mSP.play(mPowerUp3ID, 1, 1, 0, 0, 1); }
    private static void playPowerUp4Sound(){ mSP.play(mPowerUp4ID, 1, 1, 0, 0, 1); }
    private static void playHit1Sound(){ mSP.play(mHit1ID, 1, 1, 0, 0, 1); }
    public static void playInvisibleGlow(){mSP.play(mInvisiGlowID, 1,1,0,0,1);
    }

    public static void playStarSound(StarType type){
        switch(type){
            case blue:
                playPowerUp1Sound();
                break;
            case supernova:
                playPowerUp2Sound();
                break;
            case blackhole:
                playHit1Sound();
                break;
            case pink:
                playPowerUp3Sound();
                break;
            default:
                //playEatSound();
                playPowerUp4Sound();
                break;
        }
    }
}

