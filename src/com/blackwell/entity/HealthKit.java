package com.blackwell.entity;

import java.awt.*;

public class HealthKit extends GameObject {

    public HealthKit(int x, int y, ID id) {
        super(x, y, id);
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillRect(x,y, size, size);
    }
}
