
package com.example.snake;

        import android.content.Context;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.graphics.Canvas;
        import android.graphics.Paint;
        import android.graphics.Point;

// worm gains a point and loses a segment.
class PinkStar extends Star {
    // An image to represent the star
    private Bitmap mBitmapStar;
    private final StarType type = StarType.pink;
    /// Set up the apple in the constructor
    public PinkStar(Context context, Point sr, int s){
        super(sr, s);
        isActive = true;
        points = 1;
        segmentsLost = 1;
        // Load the image to the bitmap
        mBitmapStar = BitmapFactory.decodeResource(context.getResources(), R.drawable.pinkstar);

        // Resize the bitmap
        mBitmapStar = Bitmap.createScaledBitmap(mBitmapStar, s, s, false);
    }


    public void spawn(){
        Point coord = super.validCoord();
        setLocation(coord.x, coord.y);
    }

    // Draw the star
    @Override
    public void draw(Canvas canvas, Paint paint){
        canvas.drawBitmap(mBitmapStar,
                location.x * mSize, location.y * mSize, paint);
    }
    public StarType getType(){
        return type;
    }
}