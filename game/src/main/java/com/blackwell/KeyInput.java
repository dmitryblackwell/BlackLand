package com.blackwell;

import com.blackwell.entity.*;
import com.blackwell.handler.GameHandler;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class KeyInput extends KeyAdapter implements MouseListener {

    private GameHandler gameHandler;
    private Window window;

    KeyInput(GameHandler gameHandler, Window window) {
        this(gameHandler);
        this.window = window;
    }
    private KeyInput(GameHandler gameHandler) {
        this.gameHandler = gameHandler;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        Player p = gameHandler.getPlayer();
        switch (e.getKeyCode()){
            case KeyEvent.VK_W:
                p.up(); break;
            case KeyEvent.VK_S:
                p.down(); break;
            case KeyEvent.VK_A:
                p.left(); break;
            case KeyEvent.VK_D:
                p.right(); break;
            case KeyEvent.VK_SHIFT:
                gameHandler.setDrawingGUI(true); break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        Player p = gameHandler.getPlayer();
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
            case KeyEvent.VK_S:
                p.stopY();
                break;
            case KeyEvent.VK_A:
            case KeyEvent.VK_D:
                p.stopX();
                break;
            case KeyEvent.VK_ESCAPE:
                int result = JOptionPane.showConfirmDialog(null, "Exit?");
                if(result == JOptionPane.OK_OPTION)
                    window.close();
                break;
            case KeyEvent.VK_SHIFT:
                gameHandler.setDrawingGUI(false); break;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Player p = gameHandler.getPlayer();
        switch (e.getButton()){
            case MouseEvent.BUTTON1:
                if (gameHandler.isPossibleShoot()) {
                    Bullet bullet = new Bullet(p.getX(), p.getY(), e.getX(), e.getY());
                    gameHandler.add(bullet);
                    gameHandler.bulletSpam();
                }
                break;
            case MouseEvent.BUTTON3:
                if(gameHandler.isPossibleBomb()) {
                    // new bomb spawn
                    int shift = 8;
                    Bomb bomb = new Bomb(p.getX()-(p.getVelX()*shift),
                            p.getY()-(p.getVelY()*shift));
                    gameHandler.add(bomb);
                    gameHandler.bombSpam();
                }
        }
    }
    @Override
    public void mouseClicked(MouseEvent e) { }

    @Override
    public void mousePressed(MouseEvent e) { }

    @Override
    public void mouseEntered(MouseEvent e) { }

    @Override
    public void mouseExited(MouseEvent e) { }
}
