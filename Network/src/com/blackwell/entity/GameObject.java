package com.blackwell.entity;

import java.io.Serializable;
import java.util.Random;

public abstract class GameObject implements Serializable {
    protected String login;
    protected int x,y;
    protected int vX, vY;

    public String getRandomLogin(){
        Random R = new Random();
        StringBuilder sb = new StringBuilder();
        sb.append(R.nextInt(9)+1);
        for(int i=0; i<9; ++i)
            sb.append(R.nextInt(10));
        return sb.toString();
    }

    public GameObject(){
        Random R = new Random();
        x = R.nextInt(500);
        y = R.nextInt(500);
        login = getRandomLogin();
    }

    public GameObject(int x, int y) {
        this.x = x;
        this.y = y;

        login = getRandomLogin();
    }

    public GameObject(int x, int y, String login) {
        this.x = x;
        this.y = y;

        this.login = login;
    }


    @Override
    public String toString() {
        return login +"_"+ x+":"+y+";";
    }



    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if ( !(obj instanceof GameObject) )
            return false;

        return ((GameObject) obj).login.equals(this.login);
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

    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }

    public String getLogin() {
        return login;
    }
}
