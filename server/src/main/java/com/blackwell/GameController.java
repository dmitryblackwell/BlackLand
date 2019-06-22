package com.blackwell;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")
public class GameController {

    private List<Player> players = new ArrayList<>();

    @PostMapping("/update")
    public List<Player> update(Player player) {
        System.out.println("Get player: " + player.getLogin());
        System.out.println("Count of players on server: " + players.size());
        int indexOf = players.indexOf(player);
        List<Player> returnPlayers = new ArrayList<>(players);;
        if (indexOf != -1) {
            players.get(indexOf).update(player.getX(), player.getY());
            returnPlayers.remove(indexOf);
        } else {
            players.add(player);
        }

        return returnPlayers;
    }

}
