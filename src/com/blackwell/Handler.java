package com.blackwell;

import com.blackwell.entity.*;

import java.awt.*;
import java.text.SimpleDateFormat;
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
    private int enemiesCount = SPAWN_ENEMIES;
    public int spawnTime = SPAWN_TIME;
    private Random R = new Random();


    public Handler(){ init(); }

    public void init(){
        score = 0;
        objects.clear();
        toDelete.clear();
        toAdd.clear();
        lastTimeUpdate = System.currentTimeMillis() - SPAWN_TIME;

        add(new Player(WIDTH/2,HEIGHT/2,ID.Player));
        for(int i=0; i<40; ++i)
            for(int j=0; j<64; ++j)
                if (R.nextInt(4) == 0)
                    add(new Block((j + R.nextInt(20)-10)*Block.SIZE, i*Block.SIZE, ID.Block));
    }



    private void healthkitSpawn(){
        for(int i=0; i<2; ++i)
            add(new HealthKit(R.nextInt(WIDTH), R.nextInt(HEIGHT), ID.HealthKit));
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
