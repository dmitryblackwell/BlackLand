package com.blackwell.handler;

import com.blackwell.entity.Block;
import com.blackwell.entity.Maze;
import com.blackwell.entity.Wall;
import org.apache.commons.collections4.CollectionUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MazeHandler {

    private List<Wall> wallList;

    public List<Wall> getMaze() {

        if (CollectionUtils.isNotEmpty(wallList)) {
            return wallList;
        }

        wallList = new ArrayList<>();
        Maze maze = new Maze();
        ArrayList<Point> points = maze.getPoints();

        Iterator it = points.iterator();
        ArrayList<Point> toRemove = new ArrayList<>();

        Point p;
        while (it.hasNext()){
            // remove block if it is alone
            // or used before
            p = (Point) it.next();
            if (toRemove.contains(p)) {
                it.remove();
                // no point to continue - we already delete it
                continue;
            }

            toRemove.add(p);
            Point last = new Point(p.x,p.y);

            // creating wall on x
            do {
                last.x++;
                toRemove.add(new Point(last));
            } while (points.contains(last));

            int wallWidth = (last.x-p.x) * Block.SIZE;
            int wallHeight = Block.SIZE;

            // if x-wall is bad - creating wall on y
            if ((last.x - p.x) < 2) {
                last = new Point(p.x,p.y);
                do {
                    last.y++;
                    toRemove.add(new Point(last));
                }while (points.contains(last));
                wallWidth = Block.SIZE;
                wallHeight = (last.y-p.y)*Block.SIZE;
            }

            wallList.add(
                    new Wall(p.x * Block.SIZE,
                    p.y * Block.SIZE,
                    wallWidth, wallHeight)
            );
        }
        return wallList;
    }

}
