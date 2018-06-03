package com.blackwell.entity;

import java.awt.*;

public class Bullet extends GameObject {

    public Bullet(int x, int y, int moveToX, int moveToY, ID id) {
        super(x, y, id);
        size /= 5;
        speed *= cSpeed;

        vX = (moveToX - x)/speed;
        vY = (moveToY - y)/speed;

        this.x += vX*cSpeed;
        this.y += vY*cSpeed;
    }
    private int cSpeed= 2;
    private int lives = 1;

    public int getLives() { return lives; }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.MAGENTA);
        g.fillRect(x,y, size, size);
    }
}
