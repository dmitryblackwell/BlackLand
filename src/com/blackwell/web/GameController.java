package com.blackwell.web;

import com.blackwell.entity.AbstractGameEntity;
import com.blackwell.entity.Bullet;
import com.blackwell.entity.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("game/")
public class GameController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameController.class);

    private final List<AbstractGameEntity> gameEntities = new ArrayList<>();
    private long lastBulletsUpdate = System.currentTimeMillis();


    private void update(AbstractGameEntity gameEntity) {
        // TODO rewrite it with STREAM API
        for(AbstractGameEntity entity : gameEntities) {
            if (entity.equals(gameEntity)) {
                entity.update(gameEntity);
                return;
            }
        }
        gameEntities.add(gameEntity);
    }

    private <T extends AbstractGameEntity> List<T> getListOf(Class<T> clazz) {
        // TODO rewrite it with STREAM API
        List<T> list = new ArrayList<>();
        for (AbstractGameEntity gameEntity : gameEntities) {
            if (clazz.isInstance(gameEntity))
                list.add(clazz.cast(gameEntity));
        }
        return list;
    }

    @GetMapping("update")
    public List<Player> gamePlayUpdate(Player player){
        update(player);

        if ( (System.currentTimeMillis() - lastBulletsUpdate) > 10) {
            List<Bullet> bullets = getBullets();
            for (Bullet bullet : bullets) {
                bullet.updateCoords();
            }
            lastBulletsUpdate = System.currentTimeMillis();
        }

        return getPlayers();
    }

    @GetMapping("shoot")
    public void bulletShoot(Bullet bullet) {
        update(bullet);
    }

    @GetMapping("execute")
    public Object executeCmd(String cmd) {
        switch (cmd){
            case "CLR":
                gameEntities.clear();
                return "done";
            case "BULLETS":
                return getBullets();
            case "PLAYERS":
                return getPlayers();
            default:
                return "no such command";
        }
    }

    @GetMapping("get-bullets")
    public List<Bullet> getBullets() {
        return getListOf(Bullet.class);
    }

    @GetMapping("get-players")
    public List<Player> getPlayers() {
        return getListOf(Player.class);
    }


}
