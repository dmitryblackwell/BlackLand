package com.blackwell;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;

class Window extends Canvas {
    private JFrame frame;

    Window(Game game){
        frame = new JFrame("BlackLand");

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

    void close(){
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }
}
