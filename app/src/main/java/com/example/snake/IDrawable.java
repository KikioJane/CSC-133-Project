package com.example.snake;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.List;

public interface IGameObject {
    void draw(Canvas canvas, Paint paint);

    default void update(List<IGameObject> gameObjects) {
        // no implementation required (e.g. for apples)
    }
}
