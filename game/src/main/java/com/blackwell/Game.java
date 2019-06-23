package com.blackwell;

import com.blackwell.handler.GameHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;


public class Game extends Canvas implements Runnable {
    public static final int WIDTH;
    public static final int HEIGHT;
    public static final Color BG_COLOR = Color.BLACK;

    // screen init
    static {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        WIDTH = screen.width;
        HEIGHT = screen.height;
    }


    private Thread thread;
    private boolean running = false;
    private GameHandler gameHandler = new GameHandler();

    public static void main(String[] args) { new Game(); }

    private Game(){
        Window window = new Window(this);
        KeyInput keyInput = new KeyInput(gameHandler, window);
        this.addKeyListener(keyInput);
        this.addMouseListener(keyInput);

    }

    synchronized void start(){
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    private synchronized void stop(){
        try {
            thread.join();
            running = false;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while (running){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1){
                tick();
                delta--;
            }
            
            render();
            frames++;

            if(System.currentTimeMillis() - timer > 1000){
                timer += 1000;
                //System.out.println("FPS: " + frames);
                gameHandler.setFPS(frames);
                frames = 0;
            }
        }
        stop();
    }

    private void tick() {
        gameHandler.tick();
        if(gameHandler.getHealth() <= 0) {
            JOptionPane.showMessageDialog(null, "My Goodness, this is such a failure");
            gameHandler.init();
        }
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null){
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        g.setColor(BG_COLOR);
        g.fillRect(0,0,WIDTH,HEIGHT);

        gameHandler.render(g);

        g.dispose();
        bs.show();
    }
}
