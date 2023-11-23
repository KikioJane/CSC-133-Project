package com.example.snake;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.constraintlayout.widget.Group;
import androidx.core.content.res.TypedArrayUtils;

import java.util.LinkedList;

public class SnakeActivity extends Activity implements View.OnClickListener {
    // Declare an instance of SnakeGame
    private static SnakeGame mSnakeGame;

    private Button easyButton;
    private Button mediumButton;
    private Button hardButton;
    private Button highScoresButton;
    private Button scoresMenuBackButton;

    private Group mainMenuGroup;
    private Group scoresMenuGroup;

    public static SnakeGame getSnakeGame() {
        return mSnakeGame;
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
        mSnakeGame = new SnakeGame(this, size);

        setContentView(R.layout.activity_main);

        mainMenuGroup = findViewById(R.id.mainMenuGroup);
        easyButton = findViewById(R.id.easyButton);
        mediumButton = findViewById(R.id.mediumButton);
        hardButton = findViewById(R.id.hardButton);
        highScoresButton = findViewById(R.id.highScoresButton);

        scoresMenuGroup = findViewById(R.id.scoresMenuGroup);
        scoresMenuBackButton = findViewById(R.id.scoresMenuBackButton);

        for (int id : mainMenuGroup.getReferencedIds()) {
            findViewById(id).setOnClickListener(this);
        }
        scoresMenuBackButton.setOnClickListener(this);
    }

    // Start the thread in snakeEngine
    @Override
    protected void onResume() {
        super.onResume();
        mSnakeGame.resume();
    }

    // Stop the thread in snakeEngine
    @Override
    protected void onPause() {
        super.onPause();
        mSnakeGame.stop();
    }

    // Handle click events for all the menu buttons
    @Override
    public void onClick(View clickedView) {
        if (clickedView == highScoresButton) {
            // go to high scores menu
            mainMenuGroup.setVisibility(View.INVISIBLE);
            scoresMenuGroup.setVisibility(View.VISIBLE);
        } else if (clickedView == scoresMenuBackButton) {
            // go back to main menu
            scoresMenuGroup.setVisibility(View.INVISIBLE);
            mainMenuGroup.setVisibility(View.VISIBLE);
        } else if (mainMenuGroup.containsId(clickedView.getId())) {
            // set the difficulty appropriately and go to the game view
            if (clickedView == easyButton) {
                mSnakeGame.setDifficulty(Difficulty.Easy);
            } else if (clickedView == mediumButton) {
                mSnakeGame.setDifficulty(Difficulty.Medium);
            } else if (clickedView == hardButton) {
                mSnakeGame.setDifficulty(Difficulty.Hard);
            }
            setContentView(mSnakeGame);
        }
    }
}
