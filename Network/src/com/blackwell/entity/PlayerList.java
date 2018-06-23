package com.blackwell.entity;

import java.util.HashSet;

public class PlayerList extends HashSet<Player> {
    @Override
    public boolean add(Player player) {
        for(Player p : this)
            if (player.equals(p)){
                // p = player;
                p.setX(player.getX());
                p.setY(player.getY());
                return false;
            }

        return super.add(player);
    }
}
