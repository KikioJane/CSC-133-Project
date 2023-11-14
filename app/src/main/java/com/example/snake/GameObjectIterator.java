package com.example.snake;

import java.util.ArrayList;

public class GameObjectIterator implements IIterator{
    ArrayList<GameObject> list;
    public GameObjectIterator(ArrayList<GameObject> list){
        this.list = list;
    }
    @Override
    public GameObject getNext() {
        return null;
    }

    @Override
    public boolean hasNext() {
        return false;
    }
}
