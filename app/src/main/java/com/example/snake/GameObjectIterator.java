package com.example.snake;

import java.util.ArrayList;

public class GameObjectIterator implements IIterator{
    ArrayList<GameObject> list; //refactor to make private

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
        return curr < list.size() - 1;
    }
    SpaceWorm findSpaceWorm() {
        while (hasNext()) {
            GameObject curr = getNext();
            if (curr instanceof SpaceWorm) {
                return (SpaceWorm) curr;
            }
        }
        return null;
    }

    Star findStar() {
        while (hasNext()) {
            GameObject curr = getNext();
            if (curr instanceof Star) {
                return (Star) curr;
            }
        }
        return null;
    }

    BlackHole findBlackHole() {
        while (hasNext()) {
            GameObject curr = getNext();
            if (curr instanceof BlackHole) {
                return (BlackHole) curr;
            }
        }
        return null;
    }
    BlackHole findInactiveBlackHole() {
        while (hasNext()) {
            GameObject curr = getNext();
            if (curr instanceof BlackHole) {
                if(!curr.checkActive())
                    return (BlackHole) curr;
            }
        }
        return null;
    }

    boolean checkInactiveBlackHole(){
        while (hasNext()) {
            GameObject curr = getNext();
            if (curr instanceof BlackHole) {
                if (!curr.checkActive()) {
                    return true;
                }
            }
        }
        return false;
    }

    PauseResume findPauseResume() {
        while (hasNext()) {
            GameObject curr = getNext();
            if (curr instanceof PauseResume) {
                return (PauseResume) curr;
            }
        }
        return null;
    }

    WormHole findWormHole() {
        while (hasNext()) {
            GameObject curr = getNext();
            if (curr instanceof WormHole) {
                return (WormHole) curr;
            }
        }
        return null;
    }


}
