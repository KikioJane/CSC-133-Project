package com.example.snake;

import java.util.ArrayList;

public class GameObjectIterator implements IIterator{
    ArrayList<GameObject> list;
    int curr;
    public GameObjectIterator(ArrayList<GameObject> list){
        this.list = list;
        curr = -1;
    }
    @Override
    public GameObject getNext() {
        if(hasNext())
        {
            curr++;
            return list.get(curr);
        }
        return null;
    }

    @Override
    public boolean hasNext() {
        if(curr < list.size() - 1)
            return true;
        return false;
    }


}
