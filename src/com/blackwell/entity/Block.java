package com.blackwell.entity;

public class Block extends GameObject {
    private int s;

    public Block(float x, float y, int s) {
        super(x, y);
        this.s = s;
    }

    public int getS() {
        return s;
    }
}
