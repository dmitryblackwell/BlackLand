package com.blackwell.entity;

import com.blackwell.Game;

import java.awt.*;

public class Block extends GameObject {
    public static final int SIZE = 24;
    private static final int BORDER = 2;
    public Block(int x, int y){ this(x,y,ID.Block); }
    public Block(int x, int y, ID id) {
        super(x, y, id);
        size = SIZE;
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.CYAN);
        g.fillRect(x,y, size, size);

        g.setColor(Game.BG_COLOR);
        g.fillRect(x+BORDER, y+BORDER, size-BORDER*2, size-BORDER*2);
    }
}
