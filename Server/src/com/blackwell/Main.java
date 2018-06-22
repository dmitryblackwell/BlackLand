package com.blackwell;

import com.sun.xml.internal.bind.v2.model.annotation.RuntimeAnnotationReader;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class Main extends Canvas implements Runnable {

    public static void main(String[] args) {
       new Main();
    }

    Main(){
        JFrame frame = new JFrame("BlackLand");

        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.add(this);

        frame.setSize(500,500);

        frame.setVisible(true);

        (new Thread(this)).start();
    }
    public void drawRect(){
        BufferStrategy bs = getBufferStrategy();

        if (bs == null) {
            createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.RED);
        g.fillRect(50,50,50,50);

        g.dispose();
        bs.show();
    }

    @Override
    public void run() {
        while (true){
            drawRect();
        }
    }
}
