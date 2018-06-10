package com.blackwell.entity;

import java.awt.*;

public abstract class GameObject {
    protected int x, y;
    protected ID id;

    int vX, vY;
    int speed = 7;
    int size = 32;

    GameObject(int x, int y, ID id) {
        this.x = x;
        this.y = y;
        this.id = id;
    }

    public void tick(){
        x += vX;
        y += vY;
    }
    public abstract void render(Graphics g);
    public Rectangle getBounds(){
        return new Rectangle(x,y, size, size);
    }
    public boolean intersects(Rectangle rec){
        return getBounds().intersects(rec);
    }
    public boolean contains(int x, int y){
        return getBounds().contains(new Point(x,y));
    }
    public boolean contains(int x, int y, int x1, int y1){
        return (contains(x,y) || contains(x1,y1)) && contains(Math.abs(x-x1)/2,Math.abs(y-y1)/2);
    }
    public void stop(){
        vX = 0;
        vY = 0;
    }


    public void setCoord(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getSize(){ return size; }

    public int getX() { return x; }
    public void setX(int x) { this.x = x; }

    public int getY() { return y; }
    public void setY(int y) { this.y = y; }

    public ID getId() { return id; }
    public void setId(ID id) { this.id = id; }

    public int getVelX() { return vX; }
    public void setVelX(int vX) { this.vX = vX; }

    public int getVelY() { return vY; }
    public void setVelY(int vY) { this.vY = vY; }
}
