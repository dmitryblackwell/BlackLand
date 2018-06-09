package com.blackwell.entity;

import com.blackwell.Game;

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

    @Override
    public void tick() {
        super.tick();
        if ((x+size) >= Game.WIDTH)
            x = Game.WIDTH - size;
        if (x <= 0)
            x=0;

        if ((y+size) >= Game.HEIGHT)
            y = Game.HEIGHT - size;
        if (y <= 0)
            y = 0;

    }

    public void enemyCollision(){
        size -= 1;
    }
    public void healthKitCollision(){
        size += 16;
    }
    public void bulletCollision(){
        size -= 2;
    }
    public void bombCollision(){
        size -= 16;
    }
    public void bulletSpam() { size -= 4; }
    public void bombSpam() { size -= 8; }
    public boolean isPossibleShoot() { return size > 4; }
    public boolean isPossibleBomb() { return size > 8; }
    @Override
    public void render(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(x,y, size, size);

        /*
        int quoter = size/4;
        int hq = quoter/2;
        g.setColor(Color.BLUE);
        g.fillRect(x+quoter - hq/2,y+quoter - hq/2,hq,hq);
        g.fillRect(x+size-quoter -hq + hq/2,y+quoter - hq/2,hq,hq);

        g.setColor(Color.RED);
        if (vX > 0 || vY > 0)
            g.fillOval(x+quoter+hq, y+size-quoter - hq,2*hq,2*hq);
        else
            g.fillRect(x+quoter+hq, y+size-quoter - hq,2*hq,2*hq);
        */
    }
}
