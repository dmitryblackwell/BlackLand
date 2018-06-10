package com.blackwell;

import com.blackwell.entity.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import static com.blackwell.Game.BG_COLOR;
import static com.blackwell.Game.WIDTH;
import static com.blackwell.Game.HEIGHT;

public class Handler implements Iterable<GameObject> {
    private LinkedList<GameObject> objects = new LinkedList<>();
    private ArrayList<GameObject> toDelete = new ArrayList<>();
    private ArrayList<GameObject> toAdd = new ArrayList<>();
    private int score;
    private int FPS=0;
    private static final int SPAWN_ENEMIES = 20;
    private static final int SPAWN_TIME = 20_000;
    private long lastTimeUpdate;
    private int enemiesCount;
    private int spawnTime;
    private int healthkitSpawn;
    private int health;
    private int killAward = 4;
    private boolean isDrawingGUI = false;
    private Random R = new Random();


    Handler(){ init(); }

    void init(){
        score = 0;
        health = 30;
        objects.clear();
        toDelete.clear();
        toAdd.clear();
        lastTimeUpdate = System.currentTimeMillis() - SPAWN_TIME;
        enemiesCount = SPAWN_ENEMIES;
        spawnTime = SPAWN_TIME;
        healthkitSpawn = 2;

        add(new Player(WIDTH/2,HEIGHT/2,ID.Player));

        ArrayList<Point> points;

        BackMaze maze = new BackMaze();
        points = maze.getPoints();
        Iterator it;
        Point p;

        it = points.iterator();
        ArrayList<Point> toRemove = new ArrayList<>();

        while (it.hasNext()){
            // remove block if it is alone
            // or used before
            p = (Point) it.next();
            if (toRemove.contains(p)) {
                it.remove();
                continue;
            }

            toRemove.add(p);
            Point last = new Point(p.x,p.y);
            // creating wall on x
            do {
                last.x++;
                toRemove.add(new Point(last));
            }while (points.contains(last));

            // if x-wall is bad - creating wall on y
            if ((last.x - p.x) < 2){
                last = new Point(p.x,p.y);
                do {
                    last.y++;
                    toRemove.add(new Point(last));
                }while (points.contains(last));

                if ((last.y - p.y) < 2) continue;

                add(new Wall(p.x*Block.SIZE, p.y*Block.SIZE, Block.SIZE, (last.y-p.y)*Block.SIZE, ID.Wall));
                continue;
            }

            add(new Wall(p.x*Block.SIZE,p.y*Block.SIZE,(last.x-p.x)*Block.SIZE,Block.SIZE, ID.Wall));
        }

    }




    void setFPS(int FPS) {
        this.FPS = FPS;
    }

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
        Point p = new Point(R.nextInt(WIDTH), R.nextInt(HEIGHT));
//        switch (R.nextInt(4)){
//            case 0: p = new Point(R.nextInt(WIDTH), -100); break;
//            case 1: p = new Point(R.nextInt(WIDTH), HEIGHT+100); break;
//            case 2: p = new Point(-100,R.nextInt(HEIGHT)); break;
//            default:
//            case 3: p = new Point(WIDTH+100,R.nextInt(HEIGHT)); break;
//        }
        return p;
    }

    public void setDrawingGUI(boolean drawingGUI) {
        isDrawingGUI = drawingGUI;
    }

    synchronized void tick(){
        Player p = getPlayer();

        for(GameObject object : objects){
            object.tick();

            if(object.getId() == ID.BasicEnemy) {
                ((BasicEnemy) object).moveTowards(p.getX(), p.getY());
                if(p.intersects(object.getBounds())) {
                    //p.enemyCollision();
                    toDelete.add(object);
                    score++;
                    health -= killAward;
                }
            }
            if(object.getId() == ID.HealthKit &&
                    p.intersects(object.getBounds())){
                //p.healthKitCollision();
                health += killAward*4;
                toDelete.add(object);
            }

            for (GameObject o : objects){

                if (object.getId() == ID.Wall){
                    if((o.getId() == ID.BasicEnemy || o.getId() == ID.Player) &&
                            o.intersects(object.getBounds())){

                        if(object.contains(o.getX()+o.getSize()/2, o.getY() + o.getSize()))
                            o.setY(object.getY() - o.getSize());

                        if(object.contains(o.getX()+o.getSize()/2, o.getY()))
                            o.setY(object.getY() + ((Wall) object).getHeight());

                        if(object.contains(o.getX()+o.getSize(), o.getY() + o.getSize()/2))
                            o.setX(object.getX() - o.getSize());

                        if(object.contains(o.getX(), o.getY() + o.getSize()/2))
                            o.setX(object.getX() + ((Wall) object).getWith());

                    }
                }
                if (object.getId() == ID.Block || object.getId() == ID.Wall) {
                    if (o.getId() == ID.Bullet) {
                        if (object.contains(o.getX() + o.getSize() / 2, o.getY() + o.getSize()) ||
                                object.contains(o.getX() + o.getSize() / 2, o.getY())) {
                            o.setVelY(o.getVelY() * -1);
                            ((Bullet) o).minusLive();
                        }

                        if (object.contains(o.getX() + o.getSize(), o.getY() + o.getSize() / 2) ||
                                object.contains(o.getX(), o.getY() + o.getSize() / 2)) {
                            o.setVelX(o.getVelX() * -1);
                            ((Bullet) o).minusLive();
                        }

                        if (((Bullet) o).getLives() < 0)
                            toDelete.add(o);
                    }
                }
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

                }


                if(o.getId() == ID.Bomb && o.intersects(object.getBounds())){
                    if (object.getId() == ID.BasicEnemy) {
                        toDelete.add(object);
                        toDelete.add(o);
                        score++;
                        health += killAward;
                    }
                    else if (object.getId() == ID.Player) {
                        //getPlayer().bombCollision();
                        health -= killAward*2;
                        if (health < 0) health = 0;
                        toDelete.add(o);
                    }

                }

                if(object.getId() == ID.Bullet && object.intersects(o.getBounds())){
                    if(o.getId() == ID.BasicEnemy) {
                        toDelete.add(o);
                        score++;
                        health += killAward;
                    }
                    if(o.getId() == ID.Player) {
                        health -= killAward / 4;
                        if (health < 0) health = 0;
                    }
                        //((Player) o).bulletCollision();
                }
            }
        }

        for(GameObject object : toDelete)
            objects.remove(object);
        Iterator<GameObject> it = toAdd.iterator();
        while (it.hasNext()){
            objects.add(it.next());
            it.remove();
            //System.out.println("obj added");
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

    public void bulletSpam() { health -= killAward; }
    public void bombSpam() { health -= killAward*2; }
    public boolean isPossibleShoot() { return health >= killAward; }
    public boolean isPossibleBomb() { return health >= killAward*2; }

    public void render(Graphics g){
        try {
            for (GameObject object : objects) {
                object.render(g);
            }
            g.setColor(BG_COLOR);
            g.fillRect(0,0,100,110);

            g.setColor(Color.ORANGE);
            g.setFont(new Font("TimesRoman", Font.BOLD, 50));
            g.drawString(String.valueOf(score), 20, 40);
            int time = (int) (spawnTime - System.currentTimeMillis() + lastTimeUpdate);
            time /= 1000;
            int mins = time / 60;
            int secs = time - mins * 60;
            g.setFont(new Font("TimesRoman", Font.ITALIC, 30));

            g.drawString(String.format("(+)%03d", health), 20, 70);
            g.drawString(String.format("%02d:%02d", mins, secs), 20, 100);
            g.drawString(String.format("%3d",FPS),WIDTH-60,40);


            if(isDrawingGUI) {
                g.setColor(Game.BG_COLOR);
                g.fillRect(getPlayer().getX()-10, getPlayer().getY()-130, 100, 100);

                g.setColor(Color.ORANGE);
                g.setFont(new Font("TimesRoman", Font.ITALIC, 30));
                g.drawString(String.valueOf(score), getPlayer().getX() - 10, getPlayer().getY() - 100);
                g.drawString(String.format("(+)%03d", health), getPlayer().getX() - 10, getPlayer().getY() - 70);
                g.drawString(String.format("%02d:%02d", mins, secs), getPlayer().getX() - 10, getPlayer().getY() - 40);
            }


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

    public int getHealth() {
        return health;
    }

    public Iterator<GameObject> iterator() { return objects.iterator(); }

}
