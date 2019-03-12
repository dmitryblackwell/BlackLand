package com.blackwell.entity;

import java.util.Objects;

public class Player extends AbstractGameEntity {
    private String username;
    private String hexColor;
    private int health = 100;
    private int bullets;
    private String id;

    public Player(String id, float x, float y) {
        super(x, y);
        this.id = id;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if ( !(obj instanceof Player))
            return false;
        return ((Player) obj).id.equals(this.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public void update(AbstractGameEntity gameEntity) {
        super.update(gameEntity);
        if (!(gameEntity instanceof Player))
            return;
        Player p = (Player) gameEntity;
        this.health = p.health;
        this.bullets = p.bullets;
    }

    @Override
    public String toString() {
        return "Player{" +
                "username='" + username + '\'' +
                ", id='" + id + '\'' +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
