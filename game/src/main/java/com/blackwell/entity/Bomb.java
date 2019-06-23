package com.blackwell.entity;

import javax.swing.*;
import java.awt.*;

public class Bomb extends GameObject {

    public Bomb(int x, int y) { super(x,y,ID.Bomb); }
    private ImageIcon icon = new ImageIcon(getClass().getResource("/dynamite.png"));

    @Override
    public void render(Graphics g) {
        g.drawImage(icon.getImage(),x,y,null);
    }
}
