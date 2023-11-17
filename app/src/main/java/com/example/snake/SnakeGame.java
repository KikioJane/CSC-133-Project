package com.example.snake;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.LinkedList;

class SnakeGame extends SurfaceView implements Runnable{
    //Context
    private Context context;
    // Objects for the game loop/thread
    private Thread mThread = null;
    // Control pausing between updates
    private long mNextFrameTime;
    // Is the game currently playing and or paused?
    private volatile boolean mPlaying = false;
    private volatile boolean mPaused = true;


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

    // GameObjects
    private GameObjectCollection gameObjects;
    //private GameObjectIterator gameObjectIterator;
    //private SpaceWorm mSpaceWorm;
    //private Star mStar;

    //***
    private AsteroidBelt mAsteroidBelt;
    private int blockSize;
    private Background mBackground;

    private final StarFactory mStarFactory;

    // Use a linked list for O(1) time add/remove operations.
    // This doesn't really matter that much, but why not lol
    //private LinkedList<IGameObject> mGameObjects = new LinkedList<>();

    // This is the constructor method that gets called
    // from SnakeActivity
    public SnakeGame(Context context, Point size) {
        super(context);
        context = context;
        // Work out how many pixels each block is
        blockSize = size.x / NUM_BLOCKS_WIDE;
        // How many blocks of the same size will fit into the height
        mNumBlocksHigh = size.y / blockSize;

        // Initialize mSoundManager
        SoundManager.InitializeSoundManager(context);

        // Initialize the drawing objects
        mSurfaceHolder = getHolder();
        mPaint = new Paint();
        mBackground = new Background(context);

        gameObjects = new GameObjectCollection();
        //gameObjectIterator = (GameObjectIterator) gameObjects.createGameObjectIterator();
        // for asteroid belt
        createAsteroidBelt();
        // Add astroid belt
        gameObjects.addGameObject(mAsteroidBelt);

        mStarFactory = new StarFactory(context, NUM_BLOCKS_WIDE, mNumBlocksHigh, blockSize);

        // Call the constructors of our two game objects
        gameObjects.addGameObject(SpaceWorm.getSnakeInstance(context,
                                  new Point(NUM_BLOCKS_WIDE, mNumBlocksHigh),
                                  blockSize));

        //Add new Star Object
        gameObjects.addGameObject(mStarFactory.createObject());

       // mGameObjects.add(mSnake);

    }
    private SpaceWorm findSpaceWorm()
    {
        GameObjectIterator gameObjectIterator = gameObjects.createGameObjectIterator();

        while(gameObjectIterator.hasNext())
        {
            GameObject curr = gameObjectIterator.getNext();
            if(curr instanceof SpaceWorm){
                return (SpaceWorm) curr;
            }
        }
        return null;
    }
    private Star findStar()
    {
        GameObjectIterator gameObjectIterator = gameObjects.createGameObjectIterator();

        while(gameObjectIterator.hasNext())
        {
            GameObject curr = gameObjectIterator.getNext();
            if(curr instanceof Star)
            {
                return (Star) curr;
            }
        }
        return null;
    }

    // Called to start a new game
    public void newGame() {
        createAsteroidBelt();
        // reset the snake
        findSpaceWorm().reset(NUM_BLOCKS_WIDE, mNumBlocksHigh);

        // remove the other objects by clearing the list
        gameObjects.clearGameObjectList();

        //re-add Spaceworm object
        gameObjects.addGameObject(SpaceWorm.getSnakeInstance(context, new Point(NUM_BLOCKS_WIDE, mNumBlocksHigh), blockSize));
        gameObjects.addGameObject(mAsteroidBelt);
        //Add new Star Object
        gameObjects.addGameObject(mStarFactory.createObject());
        // Add astroid belt
        // mGameObjects.add(mAsteroidBelt);
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
            if(!mPaused) {
                // Update 10 times a second
                if (updateRequired()) {
                    update();
                    draw();
                }
            } else {
                // wait a moment so we don't waste CPU while paused
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }


    // Check to see if it is time for an update
    public boolean updateRequired() {

        // Run at 10 frames per second
        final long TARGET_FPS = 10;
        // There are 1000 milliseconds in a second
        final long MILLIS_PER_SECOND = 1000;

        // Are we due to update the frame
        if(mNextFrameTime <= System.currentTimeMillis()){
            // Tenth of a second has passed

            // Setup when the next update will be triggered
            mNextFrameTime =System.currentTimeMillis()
                    + MILLIS_PER_SECOND / TARGET_FPS;

            // Return true so that the update and draw
            // methods are executed
            return true;
        }

        return false;
    }


    // Update all the game objects
    public void update() {


        //for(IGameObject gameObject : mGameObjects) {
        //    gameObject.update(mGameObjects);
        //}
        // Move the snake
        findSpaceWorm().move();

        // Did the head of the snake eat the apple?
        if(findSpaceWorm().checkDinner(findStar().getLocation())){
            // This reminds me of Edge of Tomorrow.
            // One day the apple will be ready!
            findStar().spawn();

            // Add to  mScore
            mScore = mScore + 1;

            // Play a sound
            mSoundManager.playEatSound();
        }

        // Did the snake die?
        if (findSpaceWorm().detectDeath()) {
            // Pause the game ready to start again
            mSoundManager.playCrashSound();

            mPaused =true;
        }

    }


    // Do all the drawing
    public void draw() {
        // Get a lock on the mCanvas
        if (mSurfaceHolder.getSurface().isValid()) {
            mCanvas = mSurfaceHolder.lockCanvas();

            // Fill the screen with a color
            mCanvas.drawColor(Color.argb(255, 10, 44, 54));
            mBackground.draw(mCanvas, mPaint);
            // Set the size and color of the mPaint for the text
            mPaint.setColor(Color.argb(255, 255, 255, 255));
            mPaint.setTextSize(120);

            // Draw the score
            mCanvas.drawText("" + mScore, 20, 120, mPaint);

            // Draw the apple and the snake
            findStar().draw(mCanvas, mPaint);
            findSpaceWorm().draw(mCanvas, mPaint);
            mAsteroidBelt.draw(mCanvas, mPaint);

            // Draw some text while paused
            if(mPaused){
                // Draw some text while paused
                // Set the size and color of the mPaint for the text
                mPaint.setColor(Color.argb(255, 255, 255, 255));
                mPaint.setTextSize(250);

                // Draw the message
                // We will give this an international upgrade soon
                //mCanvas.drawText("Tap To Play!", 200, 700, mPaint);
                mCanvas.drawText(getResources().
                                getString(R.string.tap_to_play),
                        200, 700, mPaint);
            }
            //else {
                // draw the game objects
            //    for(IGameObject gameObject : mGameObjects) {
            //        gameObject.draw(mCanvas, mPaint);
            //    }
            //}


            // Unlock the mCanvas and reveal the graphics for this frame
            mSurfaceHolder.unlockCanvasAndPost(mCanvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                if (mPaused) {
                    mPaused = false;
                    newGame();

                    // Don't want to process snake direction for this tap
                    return true;
                }

                // Let the Snake class handle the input
                findSpaceWorm().switchHeading(motionEvent);
                break;

            default:
                break;

        }
        return true;
    }

    // Pause the game
    public void pause() {
        mPaused = true;
    }

    // Stop the thread
    public void stop() {
        mPlaying = false;
        try {
            mThread.join();
        } catch (InterruptedException e) {
            // Error
        }
    }

    // Start the thread
    public void resume() {
        mPlaying = true;
        mThread = new Thread(this);
        mThread.start();
    }

    public void incrementScore() {
        mScore++;
    }

    private void createAsteroidBelt() {
        Difficulty difficulty = Difficulty.Hard;
        mAsteroidBelt = new AsteroidBelt(this.getContext(), new Point(NUM_BLOCKS_WIDE,
                mNumBlocksHigh), blockSize, difficulty);

        mAsteroidBelt.spawn();

        SpaceWorm.setAsteroidBelt(mAsteroidBelt);
        Star.setAsteroidBelt(mAsteroidBelt);
    }
}
