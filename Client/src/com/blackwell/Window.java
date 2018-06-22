package com.blackwell;

import javax.swing.*;
import java.awt.*;

public class Window {
    Window(Canvas canvas){
        JFrame frame = new JFrame("BlackLand");

        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.add(canvas);

        frame.setSize(500,500);

        frame.setVisible(true);
    }
}
