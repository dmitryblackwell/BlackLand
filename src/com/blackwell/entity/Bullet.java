package com.blackwell.entity;

import java.util.Objects;

public class Bullet extends AbstractGameEntity {
    private float vX, vY;

    public Bullet(float x, float y, float vX, float vY) {
        super(x, y);
        this.vX = vX;
        this.vY = vY;
    }

    public float getvX() {
        return vX;
    }

    public void setvX(float vX) {
        this.vX = vX;
    }

    public float getvY() {
        return vY;
    }

    public void setvY(float vY) {
        this.vY = vY;
    }

    public void updateCoords() {
        super.x += vX;
        super.y += vY;
    }

    @Override
    public void update(AbstractGameEntity gameEntity) {
        super.update(gameEntity);
        if (! (gameEntity instanceof Bullet))
            return;
        Bullet b = (Bullet) gameEntity;
        this.vX = b.vX;
        this.vY = b.vY;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bullet bullet = (Bullet) o;
        return Float.compare(bullet.vX, vX) == 0 &&
                Float.compare(bullet.vY, vY) == 0 &&
                Float.compare(bullet.getvX(), super.x) == 0 &&
                Float.compare(bullet.getY(), super.y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.x, super.y, this.vX, this.vY);
    }

    @Override
    public String toString() {
        return "Bullet{" +
                "vX=" + vX +
                ", vY=" + vY +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
