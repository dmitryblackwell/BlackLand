package com.blackwell.entity;

public class Bullet extends GameObject {
    private float vX, vY;

    public Bullet(float x, float y, float vX, float vY){
        super(x, y);
        this.vX = vX;
        this.vY = vY;
    }


    @Override
    public void step() {
        updateCoords(vX, vY);
    }
}
