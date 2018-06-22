package com.blackwell.entity;

public class Player {
    public static final int WIDTH = 20;
    private String login;
    private int x;
    private int y;

    public Player(String login, int x, int y) {
        this.login = login;
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
