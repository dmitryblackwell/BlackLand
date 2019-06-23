package com.blackwell.handler;

import com.blackwell.entity.*;

class CollisionHandler {

    boolean isPlayerAndEnemyCollision(Player player, GameObject object) {
        if(object.getId() == ID.BasicEnemy) {
            ((BasicEnemy) object).moveTowards(player.getX(), player.getY());
            return player.intersects(object.getBounds());
        }
        return false;
    }


    boolean isPlayerAndHealthKitCollision(Player player, GameObject object) {
        return object.getId() == ID.HealthKit &&
                player.intersects(object.getBounds());
    }

    // returns true if needed to delete
    boolean isBlockAndBulletCollision(GameObject object, GameObject o) {
        if ((object.getId() == ID.Block || object.getId() == ID.Wall) &&
                o.getId() == ID.Bullet) {
            if (object.contains(o.getX() + o.getSize() / 2, o.getY() + o.getSize()) ||
                    object.contains(o.getX() + o.getSize() / 2, o.getY())) {
                o.setVelY(o.getVelY() * -1);
                ((Bullet) o).minusLive();
            }

            if (object.contains(o.getX() + o.getSize(), o.getY() + o.getSize() / 2) ||
                    object.contains(o.getX(), o.getY() + o.getSize() / 2)) {
                o.setVelX(o.getVelX() * -1);
                ((Bullet) o).minusLive();
            }

            return ((Bullet) o).getLives() < 0;
        }
        return false;
    }

    void handleWallAndPlayerOrEnemyCollision(GameObject object, GameObject o) {
        if (object.getId() == ID.Wall){
            if( (o.getId() == ID.Player || o.getId() == ID.BasicEnemy)&&
                    o.intersects(object.getBounds())){

                if(object.contains(o.getX()+o.getSize()/2, o.getY() + o.getSize()))
                    o.setY(object.getY() - o.getSize());

                if(object.contains(o.getX()+o.getSize()/2, o.getY()))
                    o.setY(object.getY() + ((Wall) object).getHeight());

                if(object.contains(o.getX()+o.getSize(), o.getY() + o.getSize()/2))
                    o.setX(object.getX() - o.getSize());

                if(object.contains(o.getX(), o.getY() + o.getSize()/2))
                    o.setX(object.getX() + ((Wall) object).getWith());

            }
        }
    }

    void handleBlockAndPlayerOrEnemyCollision(GameObject object, GameObject o) {
        if(object.getId() == ID.Block
                &&(o.getId() == ID.BasicEnemy || o.getId() == ID.Player) &&
                    o.intersects(object.getBounds())){

            if(object.contains(o.getX()+o.getSize()/2, o.getY() + o.getSize()))
                o.setY(object.getY()-o.getSize());

            if(object.contains(o.getX()+o.getSize()/2, o.getY()))
                o.setY(object.getY()+object.getSize());

            if(object.contains(o.getX()+o.getSize(), o.getY() + o.getSize()/2))
                o.setX(object.getX()-o.getSize());

            if(object.contains(o.getX(), o.getY() + o.getSize()/2))
                o.setX(object.getX()+object.getSize());

        }
    }
}
