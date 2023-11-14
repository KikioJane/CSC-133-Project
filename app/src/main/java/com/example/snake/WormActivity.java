package com.example.snake;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;

public class WormActivity extends Activity {

    // Declare an instance of SnakeGame
    private static WormGame mWormGame;

    public static WormGame getSnakeGame() {
        return mWormGame;
    }

    // Set the game up
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the pixel dimensions of the screen
        Display display = getWindowManager().getDefaultDisplay();

        // Initialize the result into a Point object
        Point size = new Point();
        display.getSize(size);

        // Create a new instance of the SnakeEngine class
        mWormGame = new WormGame(this, size);

        // Make snakeEngine the view of the Activity
        setContentView(mWormGame);
    }

    // Start the thread in snakeEngine
    @Override
    protected void onResume() {
        super.onResume();
        mWormGame.resume();
    }

    // Stop the thread in snakeEngine
    @Override
    protected void onPause() {
        super.onPause();
        mWormGame.stop();
    }
}
