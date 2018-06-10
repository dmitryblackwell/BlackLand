package com.blackwell.entity;

import java.awt.*;

public class Wall extends GameObject {

    int with, height;

    public Wall(int x, int y, int with, int height, ID id) {
        super(x, y, id);
        this.with = with;
        this.height = height;
    }

//    @Override
//    public int getSize() {
//        throw new UnsupportedOperationException();
//    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.CYAN);
        g.fillRect(x,y, with,height);
    }
}
