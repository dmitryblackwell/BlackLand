package com.blackwell.entity;

import com.blackwell.Game;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Maze {
    private int lX = (Game.WIDTH/Block.SIZE)/2;
    private int lY = (Game.HEIGHT/Block.SIZE)/2;
    private boolean[][] isVisited = new boolean[lY][lX];
    private ArrayList<Point> blocks = new ArrayList<>();
    private Random R = new Random();

    public Maze() { generate(); }

    public void generate(){
        Point p = getFreePoint();
        Point n;

        while (getFreePointSize() > 0){
            n = getFreeNeighbor(p);
            if(n == null)
                n = getFreePoint();

            try {
                isVisited[p.y][p.x + 1] = true;
                isVisited[p.y][p.x - 1] = true;
                isVisited[p.y + 1][p.x] = true;
                isVisited[p.y - 1][p.x] = true;
            } catch (Exception e){}
            p=new Point(n);
            blocks.add(p);
            isVisited[p.y][p.x] = true;
        }
    }

    public ArrayList<Point> getBlocks() {
        return blocks;
    }


    private Point getFreeNeighbor(Point p) { return getFreeNeighbor(p.x, p.y); }
    private Point getFreeNeighbor(int x, int y){
        if(!isInField(x,y)) return null;
        ArrayList<Point> n = new ArrayList<>();
        if(!isVisited[y+1][x]) n.add(new Point(x+1,y));
        if(!isVisited[y-1][x]) n.add(new Point(x-1,y));
        if(!isVisited[y][x+1]) n.add(new Point(x,y+1));
        if(!isVisited[y][x-1]) n.add(new Point(x,y-1));

        if (n.size() == 0) return null;

        int index = R.nextInt(n.size());
        return n.get(index);
    }
    private boolean isInField(int x, int y){
        return x>0 && x<lX-1 && y>0 && y<lY-1;
    }

    private ArrayList<Point> getFreePointList(){
        ArrayList<Point> freePoints = new ArrayList<>();
        for(int i=0; i<lY; ++i)
            for(int j=0; j<lX; ++j)
                if(!isVisited[i][j]) freePoints.add(new Point(j,i));
        return freePoints;
    }
    private int getFreePointSize(){ return getFreePointList().size(); }
    private Point getFreePoint(){
        ArrayList<Point> freePoints = getFreePointList();

        if(freePoints.size() == 0) return null;

        int pointIndex = R.nextInt(freePoints.size());
        return freePoints.get(pointIndex);
    }
}
