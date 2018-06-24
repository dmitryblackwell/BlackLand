package com.blackwell.entity;

import java.util.HashSet;

public class GameObjectList extends HashSet<GameObject> {
    @Override
    public boolean add(GameObject object) {
        for(GameObject o : this)
            if (object.equals(o)){
                o.setX(object.getX());
                o.setY(object.getY());
                return false;
            }

        return super.add(object);
    }
}
