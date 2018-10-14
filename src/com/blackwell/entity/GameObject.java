package com.blackwell.entity;

import java.util.Random;

public abstract class GameObject {
    private float x,y;
    private String id;


    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof GameObject))
            return false;
        if (obj == this)
            return true;
        return id.equals(((GameObject) obj).id);
    }

    public GameObject(){
        StringBuilder sb = new StringBuilder();
        Random r = new Random();
        for(int i=0; i<10; ++i)
            sb.append( (char) (r.nextInt(25)+65));
        this.id = sb.toString();
    }

    public GameObject(float x, float y, String id) {
        this.x = x;
        this.y = y;
        this.id = id;
    }

    public GameObject(float x, float y) {
        this();
        this.x = x;
        this.y = y;
    }

    public void setCoords(float x, float y){
        this.x = x;
        this.y = y;
    }

    public void updateCoords(float x, float y){
        this.x += x;
        this.y += y;
    }

    public String getId() {
        return id;
    }
    public float getX() {
        return x;
    }
    public float getY() {
        return y;
    }

    public void step() {}
}
