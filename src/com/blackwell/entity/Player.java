package com.blackwell.entity;

public class Player {
    private String name;
    private float x, y;

    public Player(String name, float x, float y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if ( !(obj instanceof Player))
            return false;
        return ((Player) obj).name.equals(this.name);
    }

    public void update(Player p){
        this.x = p.getX();
        this.y = p.getY();
    }

    @Override
    public int hashCode() {
        // TODO override his methods
        return super.hashCode();
    }



}
