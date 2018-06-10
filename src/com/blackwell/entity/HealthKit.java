package com.blackwell.entity;

import javax.swing.*;
import java.awt.*;

public class HealthKit extends GameObject {

    public HealthKit(int x, int y) {
        super(x, y, ID.HealthKit);
    }
    private ImageIcon icon = new ImageIcon("res/healthkit.png");

    @Override
    public void render(Graphics g) {
        g.drawImage(icon.getImage(),x,y,null);
    }
}
