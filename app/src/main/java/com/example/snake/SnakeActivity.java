package com.example.snake;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ViewFlipper;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;

public class SnakeActivity extends Activity implements View.OnClickListener {
    // Declare an instance of SnakeGame
    private SnakeGame mSnakeGame;

    private ViewFlipper viewFlipper;

    private Button easyButton;
    private Button mediumButton;
    private Button hardButton;
    private Button highScoresButton;
    private Button scoresMenuBackButton;

    private Button gameLayoutBackButton;

    private Group mainMenuGroup;
    private Group scoresMenuGroup;

    // Set the game up
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the pixel dimensions of the screen
        Display display = getWindowManager().getDefaultDisplay();

        // Initialize the result into a Point object
        Point size = new Point();
        display.getSize(size);

        setContentView(R.layout.activity_main);
        viewFlipper = findViewById(R.id.main_layout_view_flipper);

        ConstraintLayout gameLayout = findViewById(R.id.game_layout);

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

        gameLayoutBackButton = findViewById(R.id.gameLayoutBackButton);
        gameLayoutBackButton.setOnClickListener(this);
        // Create a new instance of the SnakeEngine class
        mSnakeGame = new SnakeGame(this, size, gameLayoutBackButton);
        gameLayout.addView(mSnakeGame, 0);
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
        // return to main menu
        viewFlipper.setDisplayedChild(0);
    }

    // Handle click events for all the menu buttons
    @Override
    public void onClick(View clickedView) {
        // TODO: figure out how to add animations between menus

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
            // go to the game view
            viewFlipper.setDisplayedChild(1);
        } else if (clickedView == gameLayoutBackButton) {
            // go back to the main menu view
            viewFlipper.setDisplayedChild(0);
            mSnakeGame.newGame();
            mSnakeGame.returnToMenu();
        }
    }
}
