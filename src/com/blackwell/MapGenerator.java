package com.blackwell;

import com.blackwell.entity.Block;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MapGenerator {
    private final int WIDTH = 800;
    private final int HEIGHT = 600;

    private static List<Block> map = new ArrayList<>();

    public static List<Block> getMap(){
        return map;
    }

    static {
        generate();
    }

    public static void generate(){
        final int SIZE = 40;
        boolean[][] isVisited = new boolean[SIZE][SIZE];
        int visitedBlocks = 0;
        Random random = new Random();
        while (visitedBlocks < 400){
            int x = random.nextInt(SIZE-5)+2;
            int y = random.nextInt(SIZE-5)+2;
            Move m = Move.getRandomMove();
            while (!isVisited[y+m.getvY()][x+m.getvX()]){
                x += m.getvX();
                y += m.getvY();
                isVisited[y][x] = true;
                switch (m){
                    case UP:
                    case DOWN:
                        isVisited[y][x-1] = true;
                        isVisited[y][x-2] = true;
                        isVisited[y][x+2] = true;
                        isVisited[y][x+1] = true;
                        break;
                    case LEFT:
                    case RIGHT:
                        isVisited[y-1][x] = true;
                        isVisited[y-2][x] = true;
                        isVisited[y+2][x] = true;
                        isVisited[y+1][x] = true;
                }
                map.add(new Block(x*20,y*20,20));
                visitedBlocks++;
                m = Move.next(m);

                if( (y+m.getvY()) >= SIZE-4 || (x+m.getvX()) >= SIZE-4
                        || (y+m.getvY()) < 3 || (x+m.getvX()) < 3)
                    break;
            }
        }

//        Move m = Move.getRandomMove();
//        int x = random.nextInt(SIZE);
//        int y = random.nextInt(SIZE);
//        do{
//            m = Move.next(m);
//            x += m.getvX();
//            y += m.getvY();
//
//            if ( y > SIZE-1 || y < 0 ||
//                    x > SIZE-1 || x < 0){
//
//            }
//        }
    }

    public static String getMapJSON(){
        // [{x:300, y:190, s:30}, {x:60, y:110, s:10}, {x:150, y:160, s:70}];
        StringBuilder sb = new StringBuilder();
        sb.append("[");

        for(Block b : map){
            sb.append("{")
                    .append("\"x\":\"").append(b.getX())
                    .append("\", \"y\":\"").append(b.getY())
                    .append("\", \"s\":\"").append(b.getS())
                    .append("\"},");
        }

        if(sb.length() > 2)
            sb.deleteCharAt(sb.length()-1);

        sb.append("]");
        return sb.toString();
    }

    enum Move{
        RIGHT(1,0), LEFT(-1,0), UP(0,-1), DOWN(0,1);
        private int vX,vY;

        Move(int vX, int vY) {
            this.vX = vX;
            this.vY = vY;
        }

        public int getvX() {
            return vX;
        }

        public int getvY() {
            return vY;
        }

        private static Move getRandomMove(Move m1, Move m2, Move m3){
            Random r = new Random();
            return r.nextBoolean() ? (r.nextBoolean() ? m1 : m2) : m3;
        }

        public static Move getRandomMove(){
            Random r = new Random();
            return Move.values()[r.nextInt(4)];
        }


        public static Move next(Move priv){
            Move next;
            switch (priv){
                case RIGHT:
                    next = getRandomMove(LEFT, UP, DOWN);
                    break;
                case LEFT:
                    next = getRandomMove(UP, RIGHT, DOWN);
                    break;
                case UP:
                    next = getRandomMove(LEFT, RIGHT, DOWN);
                    break;
                default:
                case DOWN:
                    next = getRandomMove(UP, LEFT, RIGHT);
                    break;
            }
            return next;
        }

    }

}

