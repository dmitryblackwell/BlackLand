package com.blackwell;

import com.blackwell.entity.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import static com.blackwell.Game.WIDTH;
import static com.blackwell.Game.HEIGHT;

public class Handler implements Iterable<GameObject> {
    private LinkedList<GameObject> objects = new LinkedList<>();
    private ArrayList<GameObject> toDelete = new ArrayList<>();
    private ArrayList<GameObject> toAdd = new ArrayList<>();
    private int score;
    public static final int SPAWN_ENEMIES = 20;
    public static final int SPAWN_TIME = 20_000;
    private long lastTimeUpdate;
    private int enemiesCount;
    public int spawnTime;
    public int healthkitSpawn;
    private Random R = new Random();


    public Handler(){ init(); }

    public void init(){
        score = 0;
        objects.clear();
        toDelete.clear();
        toAdd.clear();
        lastTimeUpdate = System.currentTimeMillis() - SPAWN_TIME;
        enemiesCount = SPAWN_ENEMIES;
        spawnTime = SPAWN_TIME;
        healthkitSpawn = 2;

        add(new Player(WIDTH/2,HEIGHT/2,ID.Player));

        // 1600 - 40
        // 2560 - 89

        // 1000 - 64
        // 1080 - 72


//        for(int i=0; i<HEIGHT/Block.SIZE; ++i)
//            for(int j=0; j<WIDTH/Block.SIZE; ++j)
//                if (R.nextInt(7) == 0)
//                    add(new Block((j + R.nextInt(20)-10)*Block.SIZE, i*Block.SIZE, ID.Block));

        Maze maze = new Maze();
        ArrayList<Point> blocks = maze.getBlocks();
        for(Point p : blocks)
            add(new Block(p.x*Block.SIZE, p.y*Block.SIZE));
    }
/*
    private boolean isInField(int x, int y, int lX, int lY){
        return x>=0 && x<lX && y>=0 && y<lY;
    }






    private void blocksGenerator(){
        int lX = WIDTH/Block.SIZE;
        int lY = HEIGHT/Block.SIZE;

        boolean[][] isVisited = new boolean[lY][lX];
        int vX, vY;
        int x,y,l;
        x = R.nextInt(lX);
        y = R.nextInt(lY);
        for(int i=0; i<15; ++i) {



            if(R.nextBoolean()){
                vX = 0;
                vY = R.nextBoolean() ? 1 : -1;
            }
            else {
                vX = R.nextBoolean() ? 1 : -1;
                vY = 0;
            }
            l = R.nextInt(10)+5;
            for(int j=0; j<l; ++j){
                if( !isInField(x,y,lX,lY) || isVisited[y][x]) break;

                add(new Block(x*Block.SIZE, y*Block.SIZE));
                isVisited[y][x] = true;
                if (vX == 0){
                    isVisited[y+1][x] = true;
                    isVisited[y-1][x] = true;
                }
                else {
                    isVisited[y][x+1] = true;
                    isVisited[y][x-1] = true;
                }
                x += vX;
                y += vY;
            }

        }

    }*/



    private void healthkitSpawn(){
        for(int i=0; i<healthkitSpawn; ++i)
            add(new HealthKit(R.nextInt(WIDTH), R.nextInt(HEIGHT), ID.HealthKit));
        healthkitSpawn++;
    }
    private void enemiesSpawn(){
        for(int i=0; i<enemiesCount; ++i) {
            Point p = getRandomSpawn();
            add(new BasicEnemy(p.x,p.y, ID.BasicEnemy));
        }
    }
    private Point getRandomSpawn(){
        Point p;
        switch (R.nextInt(4)){
            case 0: p = new Point(R.nextInt(WIDTH), -100); break;
            case 1: p = new Point(R.nextInt(WIDTH), HEIGHT+100); break;
            case 2: p = new Point(-100,R.nextInt(HEIGHT)); break;
            default:
            case 3: p = new Point(WIDTH+100,R.nextInt(HEIGHT)); break;
        }
        return p;
    }


    public synchronized void tick(){
        Player p = getPlayer();

        for(GameObject object : objects){
            object.tick();

            if(object.getId() == ID.BasicEnemy) {
                ((BasicEnemy) object).moveTowards(p.getX(), p.getY());
                if(p.intersects(object.getBounds())) {
                    p.enemyCollision();
                    toDelete.add(object);
                    score++;
                }
            }
            if(object.getId() == ID.HealthKit &&
                    p.intersects(object.getBounds())){
                p.healthKitCollision();
                toDelete.add(object);
            }

            for (GameObject o : objects){

                if(object.getId() == ID.Block) {
                    if ((o.getId() == ID.BasicEnemy || o.getId() == ID.Player) &&
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
                    if (o.getId() == ID.Bullet) {
                        if(object.contains(o.getX()+o.getSize()/2, o.getY() + o.getSize()) ||
                                object.contains(o.getX()+o.getSize()/2, o.getY()))
                            o.setVelX(o.getVelX()*-1);

                        if(object.contains(o.getX()+o.getSize(), o.getY() + o.getSize()/2) ||
                                object.contains(o.getX(), o.getY() + o.getSize()/2))
                            o.setVelY(o.getVelY()*-1);

                        if (((Bullet) o).getLives() < 0)
                            toDelete.add(o);
                    }
                }


                if(o.getId() == ID.Bomb && o.intersects(object.getBounds())){
                    if (object.getId() == ID.BasicEnemy) {
                        toDelete.add(object);
                        toDelete.add(o);
                        score++;
                    }
                    else if (object.getId() == ID.Player) {
                        getPlayer().bombCollision();
                        toDelete.add(o);
                    }

                }

                if(object.getId() == ID.Bullet && object.intersects(o.getBounds())){
                    if(o.getId() == ID.BasicEnemy) {
                        toDelete.add(o);
                        score++;
                    }
                    if(o.getId() == ID.Player)
                        ((Player) o).bulletCollision();
                }
            }

        }

        for(GameObject object : toDelete)
            objects.remove(object);
        Iterator<GameObject> it = toAdd.iterator();
        while (it.hasNext()){
            objects.add(it.next());
            it.remove();
            System.out.println("obj added");
        }

        toDelete.clear();



        if(System.currentTimeMillis() - lastTimeUpdate > spawnTime){
            enemiesCount += SPAWN_ENEMIES;
            spawnTime += SPAWN_TIME;

            enemiesSpawn();
            healthkitSpawn();
            lastTimeUpdate = System.currentTimeMillis();
            System.out.println("spawn");
        }
    }

    public int getScore() {
        return score;
    }

    public void render(Graphics g){
        try {
            for (GameObject object : objects) {
                object.render(g);
            }
            g.setColor(Color.ORANGE);
            g.setFont(new Font("TimesRoman", Font.BOLD, 50));
            g.drawString(String.valueOf(score), 20, 40);
            int time = (int) (spawnTime - System.currentTimeMillis() + lastTimeUpdate);
            time /= 1000;
            int mins = time / 60;
            int secs = time - mins * 60;
            g.setFont(new Font("TimesRoman", Font.ITALIC, 30));
            g.drawString(String.format("%02d:%02d", mins, secs), 20, 70);

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void add(GameObject object){
        toAdd.add(object);
    }
    public void remove(GameObject object){
        toDelete.add(object);
    }

    public Player getPlayer() {
        for(GameObject object : objects){
            if(object.getId() == ID.Player)
                return (Player) object;
        }
        return null;
    }

    public Iterator<GameObject> iterator() { return objects.iterator(); }

}
