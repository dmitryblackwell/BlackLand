package com.blackwell;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;

public class Window extends Canvas {
    private JFrame frame;

    public Window(Game game){
        frame = new JFrame("BlackLand");
//        frame.setPreferredSize(new Dimension(Game.WIDTH,Game.HEIGHT));
//        frame.setMaximumSize(new Dimension(Game.WIDTH,Game.HEIGHT));
//        frame.setMinimumSize(new Dimension(Game.WIDTH,Game.HEIGHT));

        frame.getContentPane().setBackground(Game.BG_COLOR);
        frame.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.add(game);

        ImageIcon icon = new ImageIcon("res/internet.png");
        frame.setIconImage(icon.getImage());

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);

        frame.setVisible(true);
        game.start();
    }

    public void close(){
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }
}
