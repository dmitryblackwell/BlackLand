package com.blackwell;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Window {

    public static final int WIDTH = 500;
    public static final int HEIGHT = 500;

    Window(Canvas game, String title){
        JFrame frame = new JFrame(title);

        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.add(game);

        frame.setSize(WIDTH, HEIGHT);

        frame.setVisible(true);
    }

    Window(Canvas game){
        this(game, "BlackLand");
    }
}
