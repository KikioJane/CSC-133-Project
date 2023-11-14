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
    private static int mCrashID = -1;

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

            descriptor = assetManager.openFd("snake_death.ogg");
            mCrashID = mSP.load(descriptor, 0);

        } catch (IOException e) {
            // Error
        }

    }
    // methods to access sounds
    public static void playEatSound(){
        mSP.play(mEat_ID, 1, 1, 0, 0, 1);
    }
    public static void playCrashSound(){ mSP.play(mCrashID, 1, 1, 0, 0, 1); }
}

