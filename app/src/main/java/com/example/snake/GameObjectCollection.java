package com.example.snake;
import java.util.ArrayList;
public class GameObjectCollection implements ICollection{
    private final ArrayList<GameObject> gameObjectList;

    public GameObjectCollection(){
        gameObjectList = new ArrayList<GameObject>();
    }
    public void addGameObject(GameObject obj){
        gameObjectList.add(obj);
    }
    public void removeGameObject(int index){
        gameObjectList.remove(index);
    }
    public void changeGameObject(GameObject obj1, GameObject obj2){
        int index = gameObjectList.indexOf(obj1);
        if ( index >= 0){
            gameObjectList.set(index, obj2);
        }
    }
    public void clearGameObjectList(){
        gameObjectList.clear();
    }

    @Override
    public GameObjectIterator createGameObjectIterator() {
        return new GameObjectIterator(gameObjectList);
    }
}
