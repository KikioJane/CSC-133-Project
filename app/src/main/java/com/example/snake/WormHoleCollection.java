package com.example.snake;

import java.util.ArrayList;
import java.util.LinkedList;

public class WormHoleCollection implements ICollection{
    ArrayList<GameObject> list;
    public WormHoleCollection()
    {
        list = new ArrayList<GameObject>();
    }

    public void addWormHole(WormHole wh)
    {
        if(list.size() < 3)
        {
            list.add(wh);
        }
        else
        {
            list.remove(0);
            list.add(wh);
        }
    }
    @Override
    public GameObjectIterator createGameObjectIterator() {
        return new GameObjectIterator(list);
    }
}
