package com.blackwell.entity;

public class Bullet extends GameObject {
    public static final int SIZE = 10;

    public Bullet(int x, int y, int moveToX, int moveToY){
        super(x,y);
        final int cSpeed= 2;

        final int speed = 15;

        vX = (moveToX - x)/speed;
        vY = (moveToY - y)/speed;

        this.x += vX*cSpeed;
        this.y += vY*cSpeed;
    }

}
