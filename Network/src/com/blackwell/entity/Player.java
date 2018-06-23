package com.blackwell.entity;

import java.io.Serializable;
import java.util.Random;

public class Player implements Serializable{
    public static final int SIZE = 20;
    private String login;
    private int x;
    private int y;
    private int speed = 1;
    private int vX = 0;
    private int vY = 0;

    public Player(){
        Random R = new Random();
        login = String.valueOf(R.nextInt(100));
        x = R.nextInt(450);
        x = R.nextInt(450);
        System.out.println("Player " + this + " created");
    }

    public Player(String login, int x, int y) {
        this.login = login;
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return login +"_"+ x+":"+y+";";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if ( !(obj instanceof Player) )
            return false;

        return ((Player) obj).login.equals(this.login);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        for(int i=0; i<login.length(); ++i)
            hash += 32*login.charAt(i);
        return hash;
    }

    public void tick(){
        x += vX;
        y += vY;
    }

    public void left(){ vX = -speed; }
    public void right(){ vX = speed; }
    public void up(){ vY = -speed; }
    public void down(){ vY = speed; }

    public void stopX() { vX = 0; }
    public void stopY() { vY = 0; }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }

    public String getLogin() {
        return login;
    }
}
