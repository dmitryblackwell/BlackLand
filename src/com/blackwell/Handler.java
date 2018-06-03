package com.blackwell;

import com.blackwell.entity.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class Handler implements Iterable<GameObject> {
    private LinkedList<GameObject> objects = new LinkedList<>();
    private ArrayList<GameObject> toDelete = new ArrayList<>();

    public synchronized void tick(){
        Player p = getPlayer();

        for(GameObject object : objects){
            object.tick();

            if(object.getId() == ID.BasicEnemy) {
                ((BasicEnemy) object).moveTowards(p.getX(), p.getY());
                if(p.intersects(object.getBounds())) {
                    p.enemyCollision();
                    toDelete.add(object);
                }
            }
            if(object.getId() == ID.HealthKit &&
                    p.intersects(object.getBounds())){
                p.healthKitCollision();
                toDelete.add(object);
            }

            for (GameObject o : objects){

                if(object.getId() == ID.Block) {
                    if ((o.getId() == ID.BasicEnemy || o.getId() == ID.Player) &&
                            o.intersects(object.getBounds())){
                        if(object.contains(o.getX()+o.getSize()/2, o.getY() + o.getSize()))
                            o.setY(object.getY()-o.getSize());

                        if(object.contains(o.getX()+o.getSize()/2, o.getY()))
                            o.setY(object.getY()+object.getSize());

                        if(object.contains(o.getX()+o.getSize(), o.getY() + o.getSize()/2))
                            o.setX(object.getX()-o.getSize());

                        if(object.contains(o.getX(), o.getY() + o.getSize()/2))
                            o.setX(object.getX()+object.getSize());

                    }
                    if (o.getId() == ID.Bullet) {
                        if(object.contains(o.getX()+o.getSize()/2, o.getY() + o.getSize()) ||
                                object.contains(o.getX()+o.getSize()/2, o.getY()))
                            o.setVelY(o.getVelY()*-1);

                        if(object.contains(o.getX()+o.getSize(), o.getY() + o.getSize()/2) ||
                                object.contains(o.getX(), o.getY() + o.getSize()/2))
                            o.setVelX(o.getVelX()*-1);

                        if (((Bullet) o).getLives() < 0)
                            toDelete.add(o);
                    }
                }

                if(object.getId() == ID.Bullet && object.intersects(o.getBounds())){
                    if(o.getId() == ID.BasicEnemy)
                        toDelete.add(o);
                    if(o.getId() == ID.Player)
                        ((Player) o).bulletCollision();
                }
            }

        }

        for(GameObject object : toDelete)
            objects.remove(object);
        toDelete.clear();
    }

    public void render(Graphics g){
        try {
            for (GameObject object : objects) {
                object.render(g);
            }
        }catch (Exception e) {
            e.printStackTrace();
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

    public Iterator<GameObject> iterator() { return objects.iterator(); }

}
