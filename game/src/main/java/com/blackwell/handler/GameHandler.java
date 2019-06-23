package com.blackwell.handler;

import com.blackwell.Game;
import com.blackwell.entity.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import static com.blackwell.Game.BG_COLOR;
import static com.blackwell.Game.WIDTH;
import static com.blackwell.Game.HEIGHT;

public class GameHandler implements Iterable<GameObject> {

    private Player player;
    private LinkedList<GameObject> objects = new LinkedList<>();
    private ArrayList<GameObject> toDelete = new ArrayList<>();
    private ArrayList<GameObject> toAdd = new ArrayList<>();

    private static final int SPAWN_ENEMIES = 20;
    private static final int SPAWN_TIME = 20_000;
    private static final int OBSERVABLE_FIELD = 300;

    private long lastTimeUpdate;
    private int enemiesCount;
    private int spawnTime;
    private float healthSpawn;

    private int score;
    private int health;
    private int FPS=0;

    private int killAward = 4;
    private boolean isDrawingGUI = false;
    private Random R = new Random();

    private CollisionHandler collisionHandler;


    public GameHandler(){ init(); }

    public void init(){
        score = 0;
        health = 30;
        objects.clear();
        toDelete.clear();
        toAdd.clear();
        lastTimeUpdate = System.currentTimeMillis() - SPAWN_TIME;
        enemiesCount = SPAWN_ENEMIES;
        spawnTime = SPAWN_TIME;
        healthSpawn = 2f;


        player = new Player(WIDTH/2,HEIGHT/2);
        add(player);

        collisionHandler = new CollisionHandler();
        MazeHandler mazeHandler = new MazeHandler();
        toAdd.addAll(mazeHandler.getMaze());
    }




    public void setFPS(int FPS) {
        this.FPS = FPS;
    }

    private void healthSpawn(){
        for(int i = 0; i< healthSpawn; ++i)
            add(new HealthKit(R.nextInt(WIDTH), R.nextInt(HEIGHT)));
        healthSpawn+=0.3;
    }

    private void enemiesSpawn(){
        for(int i=0; i<enemiesCount; ++i) {
            Point p = getRandomSpawn();
            add(new BasicEnemy(p.x,p.y, ID.BasicEnemy));
        }
    }

    private Point getRandomSpawn(){
         return new Point(R.nextInt(WIDTH), R.nextInt(HEIGHT));
    }

    public void setDrawingGUI(boolean drawingGUI) {
        isDrawingGUI = drawingGUI;
    }

    public synchronized void tick(){
        for(GameObject object : objects){
            object.tick();

            ////////////////////// Enemy and Player collision
            if (collisionHandler.isPlayerAndEnemyCollision(player, object)) {
                toDelete.add(object);
                score++;
                health -= killAward;
            }

            ////////////////////// HealthKit and Player collision
            if(collisionHandler.isPlayerAndHealthKitCollision(player, object)){
                health += killAward*4;
                toDelete.add(object);
            }

            for (GameObject o : objects){

                ////////////////////// Wall and Enemy/Player collision
                collisionHandler.handleWallAndPlayerOrEnemyCollision(object, o);


                ////////////////////// Block/Wall and Bullet collision
                boolean isBulletDestroyed = collisionHandler.isBlockAndBulletCollision(object, o);
                if (isBulletDestroyed)
                    toDelete.add(o);

                ////////////////////// Block and Enemy/Player collision
                collisionHandler.handleBlockAndPlayerOrEnemyCollision(object, o);


                ////////////////////// Bomb and Enemy/Player collision

                if(o.getId() == ID.Bomb && o.intersects(object.getBounds())){
                    if (object.getId() == ID.BasicEnemy) {
                        toDelete.add(object);
                        toDelete.add(o);
                        score++;
                        health += killAward;
                    }
                    else if (object.getId() == ID.Player) {
                        health -= killAward*2;
                        if (health < 0)
                            health = 0;
                        toDelete.add(o);
                    }

                }

                ////////////////////// Bullet and Enemy/Player collision
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

        // deleting objects
        for(GameObject object : toDelete)
            objects.remove(object);

        // adding objects
        Iterator<GameObject> it = toAdd.iterator();
        while (it.hasNext()){
            objects.add(it.next());
            it.remove();
        }

        toDelete.clear();



        // if time is come - spawn enemies and health
        if(System.currentTimeMillis() - lastTimeUpdate > spawnTime){
            enemiesCount += SPAWN_ENEMIES;
            spawnTime += SPAWN_TIME;

            enemiesSpawn();
            healthSpawn();
            lastTimeUpdate = System.currentTimeMillis();
            System.out.println("spawn");
        }
    }


    public void bulletSpam() { health -= killAward; }
    public void bombSpam() { health -= killAward*2; }
    public boolean isPossibleShoot() { return health >= killAward; }
    public boolean isPossibleBomb() { return health >= killAward*2; }

    public void render(Graphics g){

        try {
            for (GameObject object : objects) {

                if (Math.abs(player.getX() - object.getX()) < OBSERVABLE_FIELD &&
                        Math.abs(player.getY() - object.getY()) < OBSERVABLE_FIELD) {
                    object.render(g);
                }
            }
            // drawing foreground
            g.setColor(BG_COLOR);
            g.fillRect(0,0,100,110);

            // drawing score
            g.setColor(Color.ORANGE);
            g.setFont(new Font("TimesRoman", Font.BOLD, 50));
            g.drawString(String.valueOf(score), 20, 40);

            // convert milliseconds to mm:ss
            int time = (int) (spawnTime - System.currentTimeMillis() + lastTimeUpdate);
            time /= 1000;
            int mins = time / 60;
            int secs = time - mins * 60;

            g.setFont(new Font("TimesRoman", Font.ITALIC, 30));

            // drawing health, time, FPS
            g.drawString(String.format("(+)%03d", health), 20, 70);
            g.drawString(String.format("%02d:%02d", mins, secs), 20, 100);
            g.drawString(String.format("%3d",FPS),WIDTH-60,40);


            // if needed drawing gui on player
            if(isDrawingGUI) {
                g.setColor(Game.BG_COLOR);
                g.fillRect(player.getX()-10, player.getY()-130, 100, 100);

                g.setColor(Color.ORANGE);
                g.setFont(new Font("TimesRoman", Font.ITALIC, 30));
                g.drawString(String.valueOf(score), player.getX() - 10, player.getY() - 100);
                g.drawString(String.format("(+)%03d", health), player.getX() - 10, player.getY() - 70);
                g.drawString(String.format("%02d:%02d", mins, secs), player.getX() - 10, player.getY() - 40);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void add(GameObject object){
        toAdd.add(object);
    }


    public int getHealth() {
        return health;
    }

    public Player getPlayer() {
        return this.player;
    }

    public Iterator<GameObject> iterator() { return objects.iterator(); }

}
