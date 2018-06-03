package com.blackwell.main;

import java.awt.*;

public abstract class GameObject {
    protected int x, y;
    protected ID id;
    protected int vX, vY;
    protected int speed = 7;
    protected final int SIZE = 32;

    public GameObject(int x, int y, ID id) {
        this.x = x;
        this.y = y;
        this.id = id;
    }

    public void tick(){
        x += vX;
        y += vY;
    }
    public abstract void render(Graphics g);

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
