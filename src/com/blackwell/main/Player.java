package com.blackwell.main;

import java.awt.*;

public class Player extends GameObject {
    public Player(int x, int y, ID id) {
        super(x, y, id);
    }

    public void left() { vX = -speed; }
    public void right() { vX = +speed; }
    public void up() { vY = -speed; }
    public void down() { vY = +speed; }

    public void stopX() { vX = 0; }
    public void stopY() { vY = 0; }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(x,y,SIZE,SIZE);
    }
}
