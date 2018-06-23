package com.blackwell.entity;

import org.junit.Assert;
import org.junit.Test;

public class PlayerListTest {

    @Test
    public void PlayersAddTest(){
        System.out.println("Players Add Test");

        PlayerList list = new PlayerList();
        Player p1 = new Player("log",10,10);
        Player p2 = new Player("log",50,50);

        System.out.println("p1: " + p1);
        System.out.println("p2: " + p2);

        boolean res1 = list.add(p1);
        Assert.assertTrue(res1);

        boolean res2 = list.add(p2);
        Assert.assertFalse(res2);

        System.out.println("Adding p1 and p2 to list: " + list);

        int x_= 0, y_= 0;
        for(Player p : list)
            if ("log".equals(p.getLogin())){
                x_ = p.getX();
                y_ = p.getY();
            }
        Assert.assertEquals(x_, 50);
        Assert.assertEquals(y_, 50);


        System.out.println("Players Add Test finished");
        System.out.println("~~~~~~~~~~~~~\n");
    }
}
