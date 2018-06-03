package com.blackwell.main;

import java.awt.*;
import java.util.Iterator;
import java.util.LinkedList;

public class Handler implements Iterable<GameObject> {
    private LinkedList<GameObject> objects = new LinkedList<>();

    public void tick(){
        for(GameObject object : objects){
            object.tick();
        }
    }

    public void render(Graphics g){
        for(GameObject object : objects){
            object.render(g);
        }
    }

    public void add(GameObject object){
        objects.add(object);
    }
    public void remove(GameObject object){
        objects.remove(object);
    }

    public Player getPlayer() {
        for(GameObject object : objects){
            if(object.getId() == ID.Player)
                return (Player) object;
        }
        return null;
    }

    public Iterator iterator() { return objects.iterator(); }

}
