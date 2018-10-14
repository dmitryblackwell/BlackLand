package com.blackwell;

import com.blackwell.entity.Bullet;
import com.blackwell.entity.GameObject;
import com.blackwell.entity.Player;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GamePlay extends HttpServlet {
    private List<GameObject> gameObjects = new ArrayList<>();

    private Player getPlayerByLogin(String login){
        for(GameObject p : gameObjects)
            if (p instanceof Player && p.getId().equals(login))
                return (Player) p;
        return null;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        float x = Float.parseFloat(req.getParameter("x"));
        float y = Float.parseFloat(req.getParameter("y"));
        Player player = getPlayerByLogin(login);

        if (player == null) {
            player = new Player(x, y, login);
            gameObjects.add(player);
        }
        else
            player.setCoords(x, y);

        if (!req.getParameter("bX").equals("0")){
            float bX = Float.parseFloat(req.getParameter("bX"));
            float bY = Float.parseFloat(req.getParameter("bY"));
            float vX = Float.parseFloat(req.getParameter("vX"));
            float vY = Float.parseFloat(req.getParameter("vY"));
            Bullet bullet = new Bullet(bX, bY, vX, vY);
            gameObjects.add(bullet);
        }

        StringBuilder sb = new StringBuilder();
        sb.append("[");


        Iterator<GameObject> objectIterator = gameObjects.iterator();
        while (objectIterator.hasNext()){
            GameObject obj = objectIterator.next();
            obj.step();

            //if bullet come far - we gonna kill it
            if (obj instanceof Bullet){
                Bullet b = (Bullet) obj;
                final int BORDER = 10000;
                if (b.getX() > BORDER || b.getX() < -BORDER ||
                        b.getY() > BORDER || b.getY() < -BORDER)
                    objectIterator.remove();
            }

            if (!obj.equals(player))
                sb.append("{")
                        .append("\"x\":\"").append(obj.getX())
                        .append("\", \"y\":\"").append(obj.getY())
                        .append("\"},");
        }
        if(sb.length() > 2)
            sb.deleteCharAt(sb.length()-1);

        sb.append("]");
        resp.getWriter().write(sb.toString());
    }
}
