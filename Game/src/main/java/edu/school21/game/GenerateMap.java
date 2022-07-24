package edu.school21.game;

import java.util.*;
import java.awt.*;

public class GenerateMap {
    
    private int size;

    private  int width;
    
    private int height;

    private int enemisCount;

    private int wallsCount;

    private int maxLenght;

    private int[][] map;
    
    Point[] enemies;

    Point player;

    Point gem;


    public int[][] getMap() {
        return map;
    }

    public Point[] getEnemies() {
        return enemies;
    }

    public Point getPlayer() {
        return player;
    }

    public Point getGem() {
        return gem;
    }

    public void print() {
        for (int i = 0; i != width; i++) {
            for (int j = 0; j != height; j++) {
                if (map[i][j] != 0)
                    System.out.printf("%2d", map[i][j]);
                else
                    System.out.printf("%2s", " ");
            }
            System.out.print("\n");
        }
    }

    public boolean runPerson(Point oldPerson, Point delta) {
        int i = 0;

        if (oldPerson.x + delta.x < 0 || oldPerson.x + delta.x == width
                || oldPerson.y + delta.y < 0 || oldPerson.y + delta.y == height) {
            return false;
        }
        if (map[(int)(oldPerson.getX() + delta.getX())]
                [(int)(oldPerson.getY() + delta.getY())] != 0) {
            return false;
        }

        if (oldPerson.x == player.x && oldPerson.y == player.y) {
            map[(int)(oldPerson.getX())][(int)(oldPerson.getY())] = 0;
            map[(int)(oldPerson.getX() + delta.getX())][(int)(oldPerson.getY() + delta.getY())] = 2;
            player.setLocation(oldPerson.getX() + delta.getX(), oldPerson.getY() + delta.getY());
        } else {
            while (enemies[i] != oldPerson) {
                i++;
            }
            map[(int)(oldPerson.getX())][(int)(oldPerson.getY())] = 0;
            map[(int)(oldPerson.getX() + delta.getX())][(int)(oldPerson.getY() + delta.getY())] = 4;
            enemies[i].setLocation(oldPerson.getX() + delta.getX(), oldPerson.getY() + delta.getY());
        }
        return true;
    }

    private void generateWall(Map<Point,Integer> lenghtPlayer) {
        
        Point cord;
        Point[] arrayEnemy = new Point[enemisCount];

        for (int i = wallsCount; i != 0;) {
            cord = new Point((int)(Math.random()*(width - 1)), (int)(Math.random()*(height - 1)));
            if (lenghtPlayer.get(cord) == null) {
                map[(int)(cord.getX())][(int)(cord.getY())] = 3;
                lenghtPlayer.put(cord, 0);
                i--;
            }
        }

        for (int i = enemisCount; i != 0;) {
            cord = new Point((int)(Math.random()*(width - 1)), (int)(Math.random()*(height - 1)));
            System.out.println(cord);
            if (map[(int)(cord.getX())][(int)(cord.getY())] == 0) {
                map[(int)(cord.getX())][(int)(cord.getY())] = 4;
                arrayEnemy[enemisCount - i] = cord;
                i--;
            }
        }
        enemies = arrayEnemy;
    }

    private void generatePlayerAndWalls(int iGate, int jGate) {

        Map<Point,Integer> lengthPlayer = new HashMap<>();
        Point cord;

        maxLenght = (int)(Math.random()*maxLenght);

        map[iGate][jGate] = 1;
        cord = new Point(iGate, jGate);
        this.gem = cord;
        lengthPlayer.put(cord, 0);

        while (maxLenght != 0) {
            while (maxLenght != 0 && jGate != 0) {
                maxLenght--;
                jGate--;
                cord = new Point(iGate, jGate);
                lengthPlayer.put(cord, 0);
            }
            while (maxLenght != 0 && iGate != 0) {
                maxLenght--;
                iGate--;
                cord = new Point(iGate, jGate);
                lengthPlayer.put(cord, 0);
            }
            while (maxLenght != 0 && jGate != height - 1) {
                maxLenght--;
                jGate++;
                cord = new Point(iGate, jGate);
                lengthPlayer.put(cord, 0);
            }
            while (maxLenght != 0 && iGate != width - 1) {
                maxLenght--;
                iGate++;
                cord = new Point(iGate, jGate);
                lengthPlayer.put(cord, 0);
            }
        }
        map[iGate][jGate] = 2;
        this.player = cord;
        generateWall(lengthPlayer);
    }

    public void generateMap() throws IllegalParametersException{

        width = size;

        height = size;

        if (maxLenght <= 0) {
            throw new IllegalParametersException();
        }

        map = new int[width][height];
        generatePlayerAndWalls((int)(Math.random()*(width - 1)), (int)(Math.random()*(height - 1)));
    }

    public GenerateMap(int enemisCount, int wallsCount, int size) {
        this.size = size;
        this.enemisCount = enemisCount;
        this.wallsCount = wallsCount;
        this.maxLenght = size*size - wallsCount - enemisCount - 2;
    }
}
