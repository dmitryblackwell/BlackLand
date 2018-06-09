package com.blackwell.entity;

import javax.swing.*;
import java.awt.*;

public class HealthKit extends GameObject {

    public HealthKit(int x, int y, ID id) {
        super(x, y, id);
    }
    private ImageIcon icon = new ImageIcon("res/healthkit.png");
    @Override
    public void render(Graphics g) {
//        g.setColor(Color.GREEN);
//        g.fillRect(x,y, size, size);
        g.drawImage(icon.getImage(),x,y,null);
    }
}
