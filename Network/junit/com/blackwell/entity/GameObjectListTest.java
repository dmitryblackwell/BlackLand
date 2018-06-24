package com.blackwell.entity;

import org.junit.Assert;
import org.junit.Test;

public class GameObjectListTest {

    @Test
    public void PlayersAddTest(){
        System.out.println("Players Add Test");

        GameObjectList list = new GameObjectList();
        Player p1 = new Player(10,10,"log");
        Player p2 = new Player(50,50,"log");

        System.out.println("p1: " + p1);
        System.out.println("p2: " + p2);

        boolean res1 = list.add(p1);
        Assert.assertTrue(res1);

        boolean res2 = list.add(p2);
        Assert.assertFalse(res2);

        System.out.println("Adding p1 and p2 to list: " + list);

        int x_= 0, y_= 0;
        for(GameObject o : list)
            if ("log".equals(o.getLogin())){
                x_ = o.getX();
                y_ = o.getY();
            }
        Assert.assertEquals(x_, 50);
        Assert.assertEquals(y_, 50);


        System.out.println("Players Add Test finished");
        System.out.println("~~~~~~~~~~~~~\n");
    }
}
