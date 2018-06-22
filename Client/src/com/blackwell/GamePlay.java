package com.blackwell;

import com.blackwell.entity.Player;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GamePlay extends Canvas implements Runnable, TCPConnectionListener {
    private TCPConnection connection;
    private Random R = new Random();
    private Player hero = new Player(String.valueOf(R.nextInt(900_000)+100_000),
            R.nextInt(500), R.nextInt(500));
    private List<Player> players = new ArrayList<>();

    GamePlay(){

        new Window(this);
        (new Thread(this)).start();

        players.add(hero);
        try {
            connection = new TCPConnection(this, "192.168.31.142", PORT);
            connection.sendString(hero.getX() +" "+ hero.getY());
            System.out.println("Connection created");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void drawPlayers(Graphics g){
        g.setColor(Color.BLACK);
        for(Player p : players)
            g.fillRect(p.getX(), p.getY(), Player.WIDTH, Player.WIDTH);
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

            drawPlayers(g);

            g.dispose();
            bs.show();
        }
    }

    @Override
    public void onConnectionReady(TCPConnection tcpConnection) {
        printMessage(">>> Connection ready...");
    }

    @Override
    public void onReceiveString(TCPConnection tcpConnection, String value) {
        String[] data = value.split(" ");
        players.add(new Player("", Integer.valueOf(data[0]), Integer.valueOf(data[1])));
    }

    @Override
    public void onDisconnect(TCPConnection tcpConnection) {
        printMessage(">>> Connection close.");
    }

    @Override
    public void onException(TCPConnection tcpConnection, Exception ex) {
        printMessage(">>> Connection exception: " + ex);
    }

    private synchronized void printMessage(String msg){
        System.out.println(msg);
    }
}
