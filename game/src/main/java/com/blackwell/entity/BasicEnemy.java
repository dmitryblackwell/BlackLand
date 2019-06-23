package com.blackwell.entity;

import java.awt.*;

public class BasicEnemy extends GameObject {

    private int pX, pY;

    public BasicEnemy(int x, int y, ID id) {
        super(x, y, id);
        size /= 2;
        speed /= 2;
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(x, y, size, size);
    }


    @Override
    public void tick() {
        super.tick();
        moveTowards(pX, pY);
    }

    public void moveTowards(int x, int y){
        if (Math.abs(this.x - x) < speed &&
                Math.abs(this.y - y) < speed) {
            vX = 0;
            vY = 0;
            return;
        }
        if(this.x > x)
            vX = - speed;
        else if (this.x < x)
            vX = + speed;
        else
            vX = 0;

        if(this.y > y)
            vY = - speed;
        else if (this.y < y)
            vY = speed;
        else
            vY = 0;
    }

    public void updatePlayerCoord(int x, int y){
        this.pX = x;
        this.pY = y;
    }

    public void swapPlayerCoord(){
        int tmp = pX;
        this.pX = pY;
        this.pY = tmp;
    }



}
