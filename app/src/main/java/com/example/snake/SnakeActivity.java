package com.example.snake;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.TransitionManager;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ViewFlipper;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SnakeActivity extends Activity implements View.OnClickListener {
    // Declare an instance of SnakeGame
    private SnakeGame mSnakeGame;

    private ViewFlipper viewFlipper;

    private Button easyButton;
    private Button mediumButton;
    private Button hardButton;
    private Button highScoresButton;

    private Button scoresMenuBackButton;
    private Button scoresEasyButton;
    private Button scoresMediumButton;
    private Button scoresHardButton;
    private HighScoresAdapter highScoresAdapter;

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

        // initialize view flipper
        setContentView(R.layout.activity_main);
        viewFlipper = findViewById(R.id.main_layout_view_flipper);

        ConstraintLayout gameLayout = findViewById(R.id.game_layout);

        // get main menu objects
        mainMenuGroup = findViewById(R.id.mainMenuGroup);
        easyButton = findViewById(R.id.easyButton);
        mediumButton = findViewById(R.id.mediumButton);
        hardButton = findViewById(R.id.hardButton);
        highScoresButton = findViewById(R.id.highScoresButton);

        // get high scores menu objects
        scoresMenuGroup = findViewById(R.id.scoresMenuGroup);
        scoresMenuBackButton = findViewById(R.id.scoresMenuBackButton);
        scoresEasyButton = findViewById(R.id.scoresEasyButton);
        scoresMediumButton = findViewById(R.id.scoresMediumButton);
        scoresHardButton = findViewById(R.id.scoresHardButton);
        // Set up high scores view. We use a RecyclerView with a custom adapter so that we can
        // update the scores data dynamically.
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        highScoresAdapter = new HighScoresAdapter();
        recyclerView.setAdapter(highScoresAdapter);
        ScoresService.setHighScoresAdapter(highScoresAdapter);

        // get objects for the game layout
        gameLayoutBackButton = findViewById(R.id.gameLayoutBackButton);
        // Create a new instance of the SnakeEngine class
        mSnakeGame = new SnakeGame(this, size, gameLayoutBackButton);
        gameLayout.addView(mSnakeGame, 0);

        // register click listeners for all buttons
        for (int id : mainMenuGroup.getReferencedIds()) {
            findViewById(id).setOnClickListener(this);
        }
        for (int id : scoresMenuGroup.getReferencedIds()) {
            findViewById(id).setOnClickListener(this);
        }
        gameLayoutBackButton.setOnClickListener(this);
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
        if (clickedView == highScoresButton) {
            // go to high scores menu
            TransitionManager.beginDelayedTransition(findViewById(R.id.main_menu_layout));
            mainMenuGroup.setVisibility(View.INVISIBLE);
            scoresMenuGroup.setVisibility(View.VISIBLE);
        } else if (clickedView == scoresMenuBackButton) {
            // go back to main menu
            TransitionManager.beginDelayedTransition(findViewById(R.id.main_menu_layout));
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
        } else if (scoresMenuGroup.containsId(clickedView.getId())) {
            TransitionManager.beginDelayedTransition(findViewById(R.id.main_menu_layout), new Fade());
            // change the difficulty filter in the scores adapter
            if (clickedView == scoresEasyButton) {
                highScoresAdapter.setDifficulty(Difficulty.Easy);
            } else if (clickedView == scoresMediumButton) {
                highScoresAdapter.setDifficulty(Difficulty.Medium);
            } else if (clickedView == scoresHardButton) {
                highScoresAdapter.setDifficulty(Difficulty.Hard);
            }
        } else if (clickedView == gameLayoutBackButton) {
            // go back to the main menu view
            viewFlipper.setDisplayedChild(0);
            mSnakeGame.newGame();
            mSnakeGame.returnToMenu();
        }
    }
}
