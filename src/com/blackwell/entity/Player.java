package com.blackwell.entity;

import java.util.Random;

public class Player extends GameObject{
    public Player(){
        super();
    }

    public Player(float x, float y, String id) {
        super(x, y, id);
    }

    public Player(float x, float y) {
        super(x, y);
    }

}
