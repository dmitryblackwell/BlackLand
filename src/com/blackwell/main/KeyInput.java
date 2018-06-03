package com.blackwell.main;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {

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
        }
    }

}
