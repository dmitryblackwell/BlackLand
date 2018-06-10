package com.blackwell.entity;

import com.blackwell.Game;

import java.awt.*;

public class Player extends GameObject {
    public Player(int x, int y) {
        super(x, y, ID.Player);
        size = Block.SIZE;
    }

    public void left() { vX = -speed; }
    public void right() { vX = +speed; }
    public void up() { vY = -speed; }
    public void down() { vY = +speed; }

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

    @Override
    public void render(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(x,y, size, size);
    }
}
