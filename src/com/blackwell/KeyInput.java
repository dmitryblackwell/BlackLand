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

    public KeyInput(Handler handler, Window window) {
        this(handler);
        this.window = window;
    }
    public KeyInput(Handler handler) {
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
//            case KeyEvent.VK_SPACE:
//                p.warpJump();
//                break;
            case KeyEvent.VK_ESCAPE:
                int result = JOptionPane.showConfirmDialog(null, "Exit?");
                if(result == JOptionPane.OK_OPTION)
                    window.close();
                break;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Player p = handler.getPlayer();
        switch (e.getButton()){
            case MouseEvent.BUTTON1:
                if (p.isPossibleShoot()) {
                    handler.add(new Bullet(p.getX(), p.getY(),
                            e.getX(), e.getY(), ID.Bullet));
                    p.bulletSpam();
                }
                break;
            case MouseEvent.BUTTON3:
                if(p.isPossibleBomb()) {
                    int shift = 8;
                    handler.add(new Bomb(p.getX()-(p.getVelX()*shift),
                            p.getY()-(p.getVelY()*shift), ID.Bomb));
                    p.bombSpam();
                }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
