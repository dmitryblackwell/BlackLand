package com.blackwell.entity;

import java.awt.*;

public class Wall extends GameObject {

    private int with, height;

    public Wall(int x, int y, int with, int height) {
        super(x, y, ID.Wall);
        this.with = with;
        this.height = height;
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x,y,with,height);
    }

    @Override
    public int getSize() {
        throw new UnsupportedOperationException();
    }

    public int getWith() {
        return with;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.CYAN);
        g.fillRect(x,y, with,height);
    }
}
