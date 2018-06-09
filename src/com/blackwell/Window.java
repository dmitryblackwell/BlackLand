package com.blackwell;

import javax.swing.*;
import java.awt.*;

public class Window extends Canvas {
    public Window(Game game){
        JFrame frame = new JFrame("BlackLand");

        frame.setPreferredSize(new Dimension(Game.WIDTH,Game.HEIGHT));
        frame.setMaximumSize(new Dimension(Game.WIDTH,Game.HEIGHT));
        frame.setMinimumSize(new Dimension(Game.WIDTH,Game.HEIGHT));

        frame.getContentPane().setBackground(Game.BG_COLOR);
        frame.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.add(game);

        ImageIcon icon = new ImageIcon("res/internet.png");
        frame.setIconImage(icon.getImage());

        frame.setVisible(true);
        game.start();
    }
}
