package com.blackwell;

import com.blackwell.entity.*;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class KeyInput extends KeyAdapter implements MouseListener {

    private Handler handler;
    private Window window;

    KeyInput(Handler handler, Window window) {
        this(handler);
        this.window = window;
    }
    private KeyInput(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        Player p = handler.getPlayer();
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
                handler.setDrawingGUI(true); break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        Player p = handler.getPlayer();
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
                handler.setDrawingGUI(false); break;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Player p = handler.getPlayer();
        switch (e.getButton()){
            case MouseEvent.BUTTON1:
                if (handler.isPossibleShoot()) {
                    Bullet bullet = new Bullet(p.getX(), p.getY(), e.getX(), e.getY());
                    handler.add(bullet);
                    handler.bulletSpam();
                }
                break;
            case MouseEvent.BUTTON3:
                if(handler.isPossibleBomb()) {
                    // new bomb spawn
                    int shift = 8;
                    Bomb bomb = new Bomb(p.getX()-(p.getVelX()*shift),
                            p.getY()-(p.getVelY()*shift));
                    handler.add(bomb);
                    handler.bombSpam();
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
