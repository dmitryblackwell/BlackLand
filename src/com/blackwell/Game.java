package com.blackwell;

import com.blackwell.entity.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.Random;

public class Game extends Canvas implements Runnable {
    public static void main(String[] args) {
        new Game();
    }

    public static final int WIDTH = 1600;
    public static final int HEIGHT = 1000;

    private Thread thread;
    private boolean running = false;

    private Handler handler = new Handler();


    Game(){
        KeyInput keyInput = new KeyInput(handler);
        this.addKeyListener(keyInput);
        this.addMouseListener(keyInput);
        new Window(WIDTH, HEIGHT, "BlackLand", this);
        handler.add(new Player(WIDTH/2,HEIGHT/2,ID.Player));

        Random R = new Random();

        for(int i=0; i<70; ++i)
            handler.add(new BasicEnemy(i*20,100, ID.BasicEnemy));
        for(int i=0; i<70; ++i)
            handler.add(new BasicEnemy(i*20,HEIGHT-100, ID.BasicEnemy));
        for(int i=0; i<50; ++i)
            handler.add(new BasicEnemy(100,i*32, ID.BasicEnemy));
        for(int i=0; i<50; ++i)
            handler.add(new BasicEnemy(WIDTH-100,i*32, ID.BasicEnemy));


        for(int i=0; i<40; ++i)
            for(int j=0; j<64; ++j)
                if (R.nextInt(4) == 0)
                    handler.add(new Block((j + R.nextInt(20)-10)*Block.SIZE, i*Block.SIZE, ID.Block));

        for(int i=0; i<2; ++i)
            handler.add(new HealthKit(R.nextInt(WIDTH), R.nextInt(HEIGHT), ID.HealthKit));
    }

    public synchronized void start(){
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public synchronized void stop(){
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
                System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
        stop();
    }

    private void tick() {
        handler.tick();
        if(handler.getPlayer().getSize() <= 0) {
            JOptionPane.showMessageDialog(null, "My Goodness, this is such a failure");
            stop();
        }
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null){
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        g.setColor(Color.BLACK);
        g.fillRect(0,0,WIDTH,HEIGHT);

        handler.render(g);

        g.dispose();
        bs.show();
    }
}
