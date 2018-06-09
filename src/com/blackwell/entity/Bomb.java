package com.blackwell.entity;

import javax.swing.*;
import java.awt.*;

public class Bomb extends GameObject {

    public Bomb(int x, int y, ID id) {
        super(x, y, id);
    }
    private ImageIcon icon = new ImageIcon("res/dynamite.png");

    @Override
    public void render(Graphics g) {
//        g.setColor(Color.BLUE);
//        g.fillRect(x,y, size, size);

        g.drawImage(icon.getImage(),x,y,null);
    }
}
