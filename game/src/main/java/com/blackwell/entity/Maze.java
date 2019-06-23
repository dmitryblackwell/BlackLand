package com.blackwell.entity;

import com.blackwell.Game;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Maze {
    private int lX = (Game.WIDTH/Block.SIZE);
    private int lY = (Game.HEIGHT/Block.SIZE);
    private boolean[][] visited = new boolean[lY][lX];
    private ArrayList<Point> points = new ArrayList<>();
    private Random R = new Random();
    private int vX=1, vY=0;

    public Maze() { backGeneration(); }


    public ArrayList<Point> getPoints() {
        return points;
    }

    public void generate(){
        Point p = getFreePoint();
        Point n;

        while (getFreePointSize() > 0){
            n = getFreeNeighbor(p);
            if(n == null)
                n = getFreePoint();

            try {
                visited[p.y][p.x + 1] = true;
                visited[p.y][p.x - 1] = true;
                visited[p.y + 1][p.x] = true;
                visited[p.y - 1][p.x] = true;
            } catch (Exception e){}
            p=new Point(n);
            points.add(p);
            visited[p.y][p.x] = true;
        }
    }


    private void backGeneration(){
        while (getFreePointSize() > 500) {
            //System.out.println(getFreePointSize());
            Point p = getFreePoint();
            if (p==null) break;
            int x = p.x, y = p.y, l;
            while (getFreeNeighbor(x,y) != null) {
                l = R.nextInt(10);
                for (int i = 0; i < l; ++i) {
                    if (isInField(x, y) && !visited[y][x]) {
                        points.add(new Point(x, y));
                        visited[y][x] = true;
                    }

                    if (vX == 0) {
                        if (isInField(x + 1, y)) visited[y][x + 1] = true;
                        if (isInField(x - 1, y)) visited[y][x - 1] = true;
                    } else {
                        if (isInField(x, y - 1)) visited[y - 1][x] = true;
                        if (isInField(x, y + 1)) visited[y + 1][x] = true;
                    }


                    x += vX;
                    y += vY;
                }
                turn();
            }

        }

    }


    private Point getFreeNeighbor(Point p) { return getFreeNeighbor(p.x, p.y); }
    private Point getFreeNeighbor(int x, int y){
        if(!isInField(x,y)) return null;
        ArrayList<Point> n = new ArrayList<>();
        if(!visited[y+1][x]) n.add(new Point(x+1,y));
        if(!visited[y-1][x]) n.add(new Point(x-1,y));
        if(!visited[y][x+1]) n.add(new Point(x,y+1));
        if(!visited[y][x-1]) n.add(new Point(x,y-1));

        if (n.size() == 0) return null;

        int index = R.nextInt(n.size());
        return n.get(index);
    }

    private ArrayList<Point> getFreePointList(){
        ArrayList<Point> freePoints = new ArrayList<>();
        for(int i=0; i<lY; ++i)
            for(int j=0; j<lX; ++j)
                if(!visited[i][j]) freePoints.add(new Point(j,i));
        return freePoints;
    }

    private int getFreePointSize(){ return getFreePointList().size(); }
    private Point getFreePoint(){
        ArrayList<Point> freePoints = getFreePointList();

        if(freePoints.size() == 0) return null;

        int pointIndex = R.nextInt(freePoints.size());
        return freePoints.get(pointIndex);
    }


    private void turn(){
        if (vX == 0){
            vX = R.nextBoolean() ? -1 : 1;
            vY = 0;
        }else{
            vX = 0;
            vY = R.nextBoolean() ? -1 : 1;
        }
    }

    private boolean isFreeSpaceExists(){
        for(int i=0; i<lY; ++i)
            for(int j=0; j<lY; ++j)
                if (!visited[i][j]) return true;
        return false;
    }

    private boolean isInField(int x, int y){
        return x>0 && x<lX-1 && y>0 && y<lY-1;
    }
}