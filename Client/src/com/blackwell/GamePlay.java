package com.blackwell;

import com.blackwell.entity.GameObject;
import com.blackwell.entity.Player;
import com.blackwell.entity.GameObjectList;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.io.IOException;

public class GamePlay extends Canvas implements Runnable, TCPConnectionListener{

    private TCPConnection connection;
    private Player hero = new Player();
    private GameObjectList players = new GameObjectList();

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


        requestFocus();
        new Window(this, hero.getLogin());
        (new Thread(this)).start();
        players.add(hero);
        try {
            connection = new TCPConnection(this, "192.168.31.142", PORT);
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


            connection.sendGameObject(hero);

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
        if ( gameObject instanceof Player && !gameObject.equals(hero)) {
            System.out.println("Player " + gameObject + " received");
            players.add(gameObject);
        }
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
