package com.blackwell.entity;

import java.awt.*;

public class Bomb extends GameObject {

    public Bomb(int x, int y, ID id) {
        super(x, y, id);
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect(x,y, size, size);
    }
}
