package com.blackwell;

import com.blackwell.entity.*;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.io.IOException;

public class GamePlay extends Canvas implements Runnable, TCPConnectionListener{

    private TCPConnection connection;
    private Player hero = new Player();
    private GameObjectList players = new GameObjectList();
    private GameObjectList bullets = new GameObjectList();

    GamePlay(){
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()){
                    case KeyEvent.VK_A:
                        hero.left(); break;
                    case KeyEvent.VK_D:
                        hero.right(); break;
                    case KeyEvent.VK_S:
                        hero.down(); break;
                    case KeyEvent.VK_W:
                        hero.up(); break;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                switch (e.getKeyCode()){
                    case KeyEvent.VK_A:
                    case KeyEvent.VK_D:
                        hero.stopX(); break;
                    case KeyEvent.VK_S:
                    case KeyEvent.VK_W:
                        hero.stopY(); break;
                }
            }

        });

        addMouseListener(new MouseListener() {

            @Override
            public void mouseReleased(MouseEvent e) {
                Bullet bullet = new Bullet(hero.getX()+15, hero.getY()+15, e.getX(), e.getY());
                System.out.println("Bullet " + bullet + " created");
                connection.sendGameObject(bullet);
            }


            public void mouseEntered(MouseEvent e) {}
            public void mouseExited(MouseEvent e) {}
            public void mouseClicked(MouseEvent e) {}
            public void mousePressed(MouseEvent e) {}
        });



        requestFocus();
        new Window(this, hero.getLogin());
        (new Thread(this)).start();
        players.add(hero);
        try {
            connection = new TCPConnection(this, "192.168.100.51", PORT);
            connection.sendGameObject(hero);
            System.out.println("Connection created");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void drawPlayers(Graphics g){
        g.setColor(Color.BLACK);
        for(GameObject o : players) {
            g.fillRect(o.getX(), o.getY(), Player.SIZE, Player.SIZE);
            o.tick();
        }
    }

    private void drawBullets(Graphics g){
        g.setColor(Color.pink);
        for(GameObject o : bullets){
            g.fillRect(o.getX(), o.getY(), Bullet.SIZE, Bullet.SIZE);
            o.tick();
        }
    }

    @Override
    public void run() {
        while (true){
            BufferStrategy bs = getBufferStrategy();

            if (bs == null) {
                createBufferStrategy(3);
                continue;
            }
            Graphics g = bs.getDrawGraphics();

            g.setColor(Color.WHITE);
            g.fillRect(0,0,Window.WIDTH, Window.HEIGHT);


            drawPlayers(g);
            drawBullets(g);

            g.setColor(Color.CYAN);
            g.drawString(String.valueOf(hero.getScore()), 10,50);


            try {
                connection.sendGameObject(hero);
            }catch (NullPointerException e) {}

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            g.dispose();
            bs.show();
        }
    }

    @Override
    public void onConnectionReady(TCPConnection tcpConnection) {
        System.out.println("Connection ready");
    }

    @Override
    public void onGameObjectReceive(TCPConnection tcpConnection, GameObject gameObject) {
        if ( gameObject instanceof Player) {
            if(gameObject.equals(hero)){
                hero.setScore(((Player) gameObject).getScore());
            }else {
                //System.out.println("Player " + gameObject + " received");
                players.add(gameObject);
            }
        }
        if (gameObject instanceof Bullet){
            //System.out.println("Bullet " + gameObject + " received");
            bullets.add(gameObject);
        }
//        if (gameObject instanceof Gift){
//            System.out.println("Gift " + gameObject + " received");
//            score += ((Gift) gameObject).getSize();
//        }
    }

    @Override
    public void onDisconnect(TCPConnection tcpConnection) {
        System.out.println("Connection close");
    }

    @Override
    public void onException(TCPConnection tcpConnection, Exception ex) {
        System.out.println("Connection exception: " + ex);
    }


}
