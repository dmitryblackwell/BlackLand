package com.blackwell.entity;

import java.io.Serializable;
import java.util.Random;

public class Player extends GameObject{
    public static final int SIZE = 20;
    private int speed = 1;

    public Player(){
        super();
        System.out.println("Player " + this + " created");
    }

    public Player(int x, int y, String login) {
        super(x,y,login);
        System.out.println("Player " + this + " created");
    }

    public void left(){ vX = -speed; }
    public void right(){ vX = speed; }
    public void up(){ vY = -speed; }
    public void down(){ vY = speed; }

    public void stopX() { vX = 0; }
    public void stopY() { vY = 0; }

}
