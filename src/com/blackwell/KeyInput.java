package com.blackwell;

import com.blackwell.entity.*;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class KeyInput extends KeyAdapter implements MouseListener {

    private Handler handler;

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
        switch (e.getKeyCode()){
            case KeyEvent.VK_W:
            case KeyEvent.VK_S:
                p.stopY(); break;
            case KeyEvent.VK_A:
            case KeyEvent.VK_D:
                p.stopX(); break;
            case KeyEvent.VK_SPACE:
                p.warpJump(); break;
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
        switch (e.getButton()){
            case MouseEvent.BUTTON1:
                handler.add(new Bullet(handler.getPlayer().getX(), handler.getPlayer().getY(),
                        e.getX(), e.getY(), ID.Bullet));
                break;
            case MouseEvent.BUTTON3:
                if (Math.abs(e.getX()-handler.getPlayer().getX()) < Block.SIZE*3 &&
                    Math.abs(e.getY()-handler.getPlayer().getY()) < Block.SIZE*3)
                    handler.add(new Bomb(e.getX(), e.getY(), ID.Bomb));
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
