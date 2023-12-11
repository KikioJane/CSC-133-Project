package com.example.snake;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Button;

class SnakeGame extends SurfaceView implements Runnable {
    //Context
    private final Context context;
    // Objects for the game loop/thread
    private Thread mThread = null;
    // Control pausing between updates
    private long mNextFrameTime;
    // Is the game currently playing and or paused?
    private volatile boolean mPlaying = false;
    private volatile boolean mPaused = true;
    private volatile boolean mGameRunning = false;
    private volatile boolean mGameOver = false;
    private final Point screenSize;

    // for playing sound effects
    private SoundManager mSoundManager;

    // The size in segments of the playable area
    private final int NUM_BLOCKS_WIDE = 40;
    private final int mNumBlocksHigh;

    // How many points does the player have
    private int mScore;

    // Objects for drawing
    private Canvas mCanvas;
    private final SurfaceHolder mSurfaceHolder;
    private final Paint mPaint;
    Bitmap mPausedBitmap;
    Bitmap mResumeBitmap;

    private Difficulty difficulty = Difficulty.Easy;

    // GameObjects
    private final GameObjectCollection gameObjects;
    //***
    private AsteroidBelt mAsteroidBelt;
    private final int blockSize;
    private final Background mBackground;

    private final StarFactory mStarFactory;
    private final BlackHoleFactory mBlackHoleFactory;
    private final Button backButton;

    // Use a linked list for O(1) time add/remove operations.
    // This doesn't really matter that much, but why not lol
    //private LinkedList<IGameObject> mGameObjects = new LinkedList<>();

    // This is the constructor method that gets called
    // from SnakeActivity
    public SnakeGame(Context context, Point size, Button backButton) {
        super(context);
        this.context = context;
        screenSize = size;
        // Work out how many pixels each block is
        blockSize = size.x / NUM_BLOCKS_WIDE;
        // How many blocks of the same size will fit into the height
        mNumBlocksHigh = size.y / blockSize;

        this.backButton = backButton;

        // Initialize mSoundManager
        SoundManager.InitializeSoundManager(context);

        // Initialize the drawing objects
        mSurfaceHolder = getHolder();
        mPaint = new Paint();
        mBackground = new Background(context, size);

        setBitmaps();

        gameObjects = new GameObjectCollection();

        // Add asteroid belt
        mAsteroidBelt = AsteroidBelt.getInstance(new Point(NUM_BLOCKS_WIDE,
                mNumBlocksHigh), blockSize, difficulty);

        mStarFactory = new StarFactory(context, NUM_BLOCKS_WIDE, mNumBlocksHigh, blockSize);
        mBlackHoleFactory = new BlackHoleFactory(context, NUM_BLOCKS_WIDE, mNumBlocksHigh,
                blockSize);

        addGameObjects();
    }

    // Method to add game objects into the game object collection
    private void addGameObjects() {
        // For asteroid belt
        createAsteroidBelt();

        // Add asteroid belt
        gameObjects.addGameObject(mAsteroidBelt);

        // Call the constructors of our two game objects
        gameObjects.addGameObject(SpaceWorm.getSnakeInstance(context,
                new Point(NUM_BLOCKS_WIDE, mNumBlocksHigh),
                blockSize));

        // Add new Star Object
        gameObjects.addGameObject(mStarFactory.createObject());
    }


    private void setBitmaps() {
        mPausedBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.pause_icon);
        mPausedBitmap = Bitmap.createScaledBitmap(mPausedBitmap, 100, 100, false);
        mResumeBitmap = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.resume_icon);
        mResumeBitmap = Bitmap.createScaledBitmap(mResumeBitmap, 100, 100, false);
    }

    // Called to start a new game
    public void newGame() {
        mGameOver = false;
        // set difficulty levels
        mStarFactory.setDifficulty(difficulty);
        mBlackHoleFactory.setDifficulty(difficulty);
        mBlackHoleFactory.setCount(0);
        // change background placement
        mBackground.resetPlacement();
        // reset the snake
        SpaceWorm spaceWorm = gameObjects.createGameObjectIterator().findSpaceWorm();
        spaceWorm.reset(NUM_BLOCKS_WIDE, mNumBlocksHigh);
        spaceWorm.resetInvisible(context);

        //createAsteroidBelt();
        mAsteroidBelt.spawn(this.context, difficulty);

        // remove the other objects by clearing the list
        gameObjects.clearGameObjectList();
        addGameObjects();

        // Reset the mScore
        mScore = 0;

        // Setup mNextFrameTime so an update can triggered
        mNextFrameTime = System.currentTimeMillis();
    }


    // Handles the game loop
    @Override
    public void run() {
        // Wait a second for the activity to fully load before drawing. Admittedly, I am not a big
        // fan of this solution, but I can't find a better one so this will do for now.
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // draw the first frame
        draw();

        while (mPlaying) {
            // Update 10 times a second
            if (updateRequired()) {
                if (mGameRunning && !mPaused) {
                    update();
                }
                draw();
                if (mPaused) {
                    // wait a moment so we don't waste CPU while paused
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    // Check to see if it is time for an update
    public boolean updateRequired() {
        final long TARGET_FPS;
        switch (difficulty) {
            case Easy:
                TARGET_FPS = 4;
                break;
            case Medium:
                TARGET_FPS = 7;
                break;
            case Hard:
                TARGET_FPS = 12;
                break;
            default:
                // this branch shouldn't be hit so it doesn't really matter what's here
                TARGET_FPS = 1;
                break;
        }
        // There are 1000 milliseconds in a second
        final long MILLIS_PER_SECOND = 1000;

        // Are we due to update the frame
        if (mNextFrameTime <= System.currentTimeMillis()) {
            // Tenth of a second has passed

            // Setup when the next update will be triggered
            mNextFrameTime = System.currentTimeMillis()
                    + MILLIS_PER_SECOND / TARGET_FPS;

            // Return true so that the update and draw
            // Methods are executed
            return true;
        }

        return false;
    }


    // Update all the game objects
    public void update() {
        SpaceWorm spaceWorm = gameObjects.createGameObjectIterator().findSpaceWorm();

        // Move the snake
        spaceWorm.move();
        spaceWorm.updateInvisible(context);

        // Did the head of the snake eat the apple?
        Star star = gameObjects.createGameObjectIterator().findStar();
        if(star != null && spaceWorm.checkDinner(star.getLocation(), star.segmentsAdded())){
            // This reminds me of Edge of Tomorrow.
            // One day the apple will be ready!
            if (gameObjects.createGameObjectIterator().findStar().getType() == StarType.blue){
                // set invisibility count to 0 in the event that the worm is already invisible
                gameObjects.createGameObjectIterator().findSpaceWorm().setInvisibilityCount(0);
                spaceWorm.setInvisible(context);
            }
            // Add to  mScore
            addToScore(gameObjects.createGameObjectIterator().findStar().points());

            // Updates to a a new kind of star
            gameObjects.changeGameObject(gameObjects.createGameObjectIterator().findStar(), mStarFactory.createObject());
            gameObjects.createGameObjectIterator().findStar().spawn();


            // Play a sound
            mSoundManager.playStarSound(star.getType());
        }
        else { // this stuff is for the supernova star
            if (star.getType() == StarType.supernova){
                star.updateStar();
            }
            if (!star.checkActive()){
                // if the star is inactive, change the star.
                gameObjects.changeGameObject(gameObjects.createGameObjectIterator().findStar(), mStarFactory.createObject());
                gameObjects.createGameObjectIterator().findStar().spawn();
            }
        }

        // TODO: Make spawns based on amount of stars
        // Did the head of the snake go into a black hole
        int i = 0; // Keep count of number of black holes
        int tempScore = mScore; // To detect death
        for (GameObject o : gameObjects.createGameObjectIterator().list) {
            if (o instanceof BlackHole) {
                i++;
                if(spaceWorm.checkDinner(o.getLocation(), gameObjects.createGameObjectIterator().findBlackHole().segmentsAdded())) {

                    // Subtract from  mScore if worm is not invisible
                    if (!gameObjects.createGameObjectIterator().findSpaceWorm().getInvisible())
                        addToScore(- gameObjects.createGameObjectIterator().findBlackHole().points(mScore));


                    if(spaceWorm.getSegmentsCount() <= 1) {// worm will die if it eats a black hole with one segment left
                        tempScore = -1;
                    }

                    if(tempScore == -1) {
                        break;
                    }

                    // Move black hole off screen
                    o.getLocation().x = -1;
                    o.getLocation().y = -1;

                    // Respawn only if score is higher than factor
                    int spawnRate = mBlackHoleFactory.getSpawnRate();
                    if(mScore >= spawnRate * i && !gameObjects.createGameObjectIterator().findSpaceWorm().getInvisible())
                        o.spawn();
                    else{
                        mBlackHoleFactory.setCount(mBlackHoleFactory.getCount() - 1);
                    }
                    // Play a sound
                    mSoundManager.playStarSound(gameObjects.createGameObjectIterator().findBlackHole().getType());
                }
            }
        }

        // Did the snake die?
        if (tempScore <= -1 || spaceWorm.detectDeath()>0) {
            // Determine which death sound to play
            if (spaceWorm.detectDeath() == 2){
                mSoundManager.playAsteroidCrashSound();
            }
            else{
                mSoundManager.playEdgeCrashSound();
            }

            // reset the worm to visible
            gameObjects.createGameObjectIterator().findSpaceWorm().setInvisibilityCount(0);
            gameObjects.createGameObjectIterator().findSpaceWorm().resetInvisible(context);
            // Pause the game ready to start again
            mPaused = true;
            mGameRunning = false;
            mGameOver = true;
            setBackButtonVisibilityOnUiThread(VISIBLE);
            ScoresService.addScore(mScore);
        }
    }

    // Do all the drawing
    public void draw() {
        // Get a lock on the mCanvas
        if (mSurfaceHolder.getSurface().isValid()) {
            mCanvas = mSurfaceHolder.lockCanvas();

            // Draw the background
            mBackground.draw(mCanvas, mPaint);

            // Set the size and color of the mPaint for the text
            mPaint.setColor(Color.argb(255, 255, 255, 255));
            mPaint.setTextSize(120);

            // Draw the score
            mCanvas.drawText("" + mScore, 20, 120, mPaint);

            if (gameObjects.createGameObjectIterator().findStar() != null) {  // prevents crash caused by null star reference
                gameObjects.createGameObjectIterator().findStar().draw(mCanvas, mPaint);
            }
            for(GameObject o : gameObjects.createGameObjectIterator().list){
                if(o instanceof BlackHole) {
                    ((BlackHole) o).draw(mCanvas, mPaint);
                }
            }
            if(gameObjects.createGameObjectIterator().findSpaceWorm() != null)
                gameObjects.createGameObjectIterator().findSpaceWorm().draw(mCanvas, mPaint);
            mAsteroidBelt.draw(mCanvas, mPaint);

            if (mPaused) {
                if (mGameRunning) {
                    // Darken the screen when paused during gameplay
                    mCanvas.drawColor(Color.argb(127, 0, 0, 0));

                    // Draw resume icon
                    mCanvas.drawBitmap(mResumeBitmap, screenSize.x - 125, 25, mPaint);
                    // Draw resume text
                    mPaint.setColor(Color.argb(255, 255, 255, 255));
                    mPaint.setTextSize(60);
                    mCanvas.drawText(getResources().getString(R.string.tap_to_resume),
                            screenSize.x - 530, 95, mPaint);
                } else {
                    // Set the size and color of the mPaint for the text
                    mPaint.setColor(Color.argb(255, 255, 255, 255));

                    // Draw the message
                    if (mGameOver) {
                        // Draw red overlay on screen
                        mCanvas.drawColor(Color.argb(80, 255, 0, 0));

                        mPaint.setTextSize(200);
                        mCanvas.drawText(getResources().getString(R.string.game_over),
                                200, 400, mPaint);

                        mPaint.setTextSize(100);
                        mCanvas.drawText(String.format("%s: %d", getResources().getString(R.string.score), mScore),
                                220, 530, mPaint);

                        mPaint.setTextSize(76);
                        mCanvas.drawText(getResources().getString(R.string.tap_to_play_again),
                                220, 650, mPaint);
                    } else {
                        mPaint.setTextSize(200);
                        mCanvas.drawText(getResources().getString(R.string.tap_to_play),
                                50, 400, mPaint);
                    }
                }
            } else {
                // Draw pause icon
                mCanvas.drawBitmap(mPausedBitmap, screenSize.x - 125, 25, mPaint);
            }

            // Unlock the mCanvas and reveal the graphics for this frame
            mSurfaceHolder.unlockCanvasAndPost(mCanvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                if (mPaused && !mGameRunning) {
                    // start a new game
                    newGame();
                    mPaused = false;
                    mGameRunning = true;
                    backButton.setVisibility(GONE);
                } else if (motionEvent.getX() > screenSize.x - 150 && motionEvent.getY() < 150) {
                    // The player hit the pause/resume button
                    if (mPaused) {
                        mPaused = false;
                        backButton.setVisibility(INVISIBLE);
                    } else {
                        mPaused = true;
                        backButton.setVisibility(VISIBLE);
                    }
                    // draw immediately so that the screen fade/unfade matches up with the button hide/unhide
                    draw();
                } else if (!mPaused) {
                    // Let the Snake class handle the input
                    gameObjects.createGameObjectIterator().findSpaceWorm().switchHeading(motionEvent);
                }
                break;
            default:
                break;
        }
        return true;
    }

    // called to reset the game state when we return to main menu
    public void returnToMenu() {
        mPaused = true;
        mGameRunning = false;
        backButton.setVisibility(GONE);
    }

    private void setBackButtonVisibilityOnUiThread(int visibility) {
        ((Activity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                backButton.setVisibility(visibility);
            }
        });
    }

    // Stop the thread
    public void stop() {
        mPlaying = false;
        mPaused = true;
        mGameRunning = false;
        mGameOver = false;
        try {
            mThread.join();
        } catch (InterruptedException e) {
            // Error
        }
        setBackButtonVisibilityOnUiThread(GONE);
    }

    // Start the thread
    public void resume() {
        mPlaying = true;
        mThread = new Thread(this);
        mThread.start();
    }

    private void createAsteroidBelt() {
        mAsteroidBelt = AsteroidBelt.getInstance(new Point(NUM_BLOCKS_WIDE,
                mNumBlocksHigh), blockSize, difficulty);
        mAsteroidBelt.spawn();
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    void addToScore(int point){
        if(point >= 0) {
            for (int i = 0; i < point; i++) {
                mScore += 1;
                updateBlackHoleSpawn();
            }
        }
        else if(point < 0){
            for (int i = 0; i < (-1) * point; i++) {
                mScore -= 1;
            }
        }
    }

    private void updateBlackHoleSpawn(){
        int spawnRate = mBlackHoleFactory.getSpawnRate();
        if(mScore % spawnRate == 0 && mScore != 0) {
            gameObjects.addGameObject(mBlackHoleFactory.createObject());
        }
    }
}
