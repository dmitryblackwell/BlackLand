package com.blackwell.entity;

import java.awt.*;

public class Player extends GameObject {
    public Player(int x, int y, ID id) {
        super(x, y, id);
    }
    private int warpSize = 10;

    public void left() { vX = -speed; }
    public void right() { vX = +speed; }
    public void up() { vY = -speed; }
    public void down() { vY = +speed; }

    public void warpJump() {
        x += warpSize*vX;
        y += warpSize*vY;
    }

    public void stopX() { vX = 0; }
    public void stopY() { vY = 0; }

    public void enemyCollision(){
        size -= 2;
    }
    public void healthKitCollision(){
        size += 16;
    }
    public void bulletCollision(){
        size -= 4;
    }
    @Override
    public void render(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(x,y, size, size);
    }
}
