package com.blackwell.entity;

import java.awt.*;

public class Block extends GameObject {
    public static final int SIZE = 24;

    public Block(int x, int y) {
        super(x, y, ID.Block);
        size = SIZE;
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.CYAN);
        g.fillRect(x,y, size, size);
    }
}
