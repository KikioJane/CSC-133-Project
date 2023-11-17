package com.example.snake;
import java.util.ArrayList;
public class GameObjectCollection implements ICollection{
    private ArrayList<GameObject> gameObjectList;

    public GameObjectCollection(){
        gameObjectList = new ArrayList<GameObject>();
    }
    public void addGameObject(GameObject obj){
        gameObjectList.add(obj);
    }
    public void removeGameObject(int index){
        gameObjectList.remove(index);
    }
    public void changeGameObject(){

    }
    public void clearGameObjectList(){
        gameObjectList.clear();
    }

    @Override
    public GameObjectIterator createGameObjectIterator() {
        return new GameObjectIterator(gameObjectList);
    }
}
