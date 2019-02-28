package com.blackwell.web;

import com.blackwell.entity.Player;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("game/")
public class GameController {

    private final List<Player> players = new ArrayList<>();

    private boolean updatePlayer(Player player) {
        for(Player p : players)
            if (p.equals(player)) {
                p.update(player);
                return true;
            }
        return false;
    }

    @GetMapping("update")
    public List<Player> gamePlayUpdate(Player player){
        boolean result = updatePlayer(player);
        if (!result)
            players.add(player);
        return players;
    }

}
