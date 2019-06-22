package com.blackwell.entity;

import java.awt.*;

public class Bullet extends GameObject {

    private int lives = 100;

    public Bullet(int x, int y, int moveToX, int moveToY) {
        super(x, y, ID.Bullet);

        final int cSpeed= 2;

        size /= 5;
        speed *= cSpeed;

        vX = (moveToX - x)/speed;
        vY = (moveToY - y)/speed;

        this.x += vX*cSpeed;
        this.y += vY*cSpeed;
    }


    public int getLives() { return lives; }
    public void minusLive() {lives--;}

    @Override
    public void render(Graphics g) {
        g.setColor(Color.MAGENTA);
        g.fillRect(x,y, size, size);
    }
}
