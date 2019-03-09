package com.blackwell.entity;

public abstract class AbstractGameEntity {

    protected float x, y;

    public AbstractGameEntity(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void update(AbstractGameEntity gameEntity) {
        this.x = gameEntity.x;
        this.y = gameEntity.y;
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
}
